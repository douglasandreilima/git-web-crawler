package dev.douglas.api.services;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import dev.douglas.api.info.GitWebCralwerItem;
import dev.douglas.api.info.GitWebCralwerResponse;
import dev.douglas.api.validators.GitUrlPossibleFileValidator;
import dev.douglas.api.validators.GitUrlRepositoryValidator;
import io.quarkus.scheduler.Scheduled;

@ApplicationScoped
public class GitWebCrawlerServiceImpl implements GitWebCrawlerService {

	private final static Logger LOG = LoggerFactory.getLogger(GitWebCrawlerServiceImpl.class);

	private final static Cache<String, GitWebCralwerResponse> GIT_WEB_CRAWLER_RESPONSE_CACHE = CacheBuilder.newBuilder()
			.maximumSize(1000).expireAfterWrite(Duration.ofHours(4)).build();

	private static final BlockingQueue<URL> URLS_QUEUE = new LinkedBlockingDeque<>();

	private static final BlockingQueue<URL> URLS_RUNNING_QUEUE = new ArrayBlockingQueue<>(10);

	@Inject
	GitUrlRepositoryValidator gitUrlValidator;

	@Inject
	GitUrlPossibleFileValidator gitUrlPossibleFileValidator;

	@Inject
	ContentPageFetcherService contentPageFetcherService;

	@Override
	public GitWebCralwerResponse calcLinesAndBytesAllFilesFromGitUrl(final String urlRequest) throws Exception {

		try {
			final URL url = new URL(urlRequest);
			if (!gitUrlValidator.test(url)) {
				throw new BadRequestException("Invalid parameter URL - only accept github repository url");
			}

			final GitWebCralwerResponse response = GIT_WEB_CRAWLER_RESPONSE_CACHE.getIfPresent(url.toExternalForm());

			if (response != null) {
				return response;
			}

			if (!URLS_QUEUE.contains(url) && !URLS_RUNNING_QUEUE.contains(url)) {
				URLS_QUEUE.add(url);
			}

			return GitWebCralwerResponse.newInstance(
					"Request successful, your repository is being processed, please try again after a few seconds");

		} catch (final MalformedURLException e) {
			throw new BadRequestException("Invalid parameter URL");
		}
	}

	@Scheduled(every = "1s")
	public void runAsyncProcessRequest() {

		URL url = null;
		try {
			url = URLS_QUEUE.poll();
			if (url != null && !URLS_RUNNING_QUEUE.contains(url)) {
				final Stopwatch stopwatch = Stopwatch.createStarted();
				URLS_RUNNING_QUEUE.add(url);

				GitWebCralwerResponse response;
				response = fetchGitHubFilesInfo(url);
				GIT_WEB_CRAWLER_RESPONSE_CACHE.put(url.toExternalForm(), response);

				URLS_RUNNING_QUEUE.remove(url);

				LOG.info("Processed url={} in {}", url, stopwatch.elapsed());
			}
		} catch (final Exception e) {
			URLS_RUNNING_QUEUE.remove(url);
			LOG.error("Error processing url={}", url, e);
		}
	}

	private GitWebCralwerResponse fetchGitHubFilesInfo(final URL url) throws Exception {

		final GitWebCralwerResponse response = new GitWebCralwerResponse();
		response.setUrlRepository(url.toExternalForm());
		response.setFiles(fetchAllFilesItems(url));

		return response;
	}

	private Map<String, List<GitWebCralwerItem>> fetchAllFilesItems(final URL url) throws Exception {

		final Map<String, List<GitWebCralwerItem>> fileItems = new HashMap<>();

		final Queue<URI> links = new LinkedList<>();
		links.add(url.toURI());

		do {

			final Document dom = Jsoup.parse(contentPageFetcherService.fetchContentPage(links.poll()));
			final Elements select = dom.select(".js-details-container .js-navigation-open[data-pjax]");

			for (final Element element : select) {

				final String urlPath = element.attr("href");
				if (gitUrlPossibleFileValidator.test(urlPath)) {

					final Optional<GitWebCralwerItem> optionalItem = fetchFileItem(url, urlPath);

					if (optionalItem.isPresent()) {

						List<GitWebCralwerItem> listItems = fileItems.get(optionalItem.get().getFileExtension());
						if (listItems == null) {
							listItems = new ArrayList<>();
							fileItems.put(optionalItem.get().getFileExtension(), listItems);
						}
						listItems.add(optionalItem.get());
						continue;
					}

				}

				final URL linkUrl = new URL(url, urlPath);
				links.add(linkUrl.toURI());
			}

			TimeUnit.MILLISECONDS.sleep(2);

		} while (!links.isEmpty());

		return fileItems;

	}

	private Optional<GitWebCralwerItem> fetchFileItem(final URL urlHost, final String url) throws Exception {

		try {

			final URL fileUrl = new URL(urlHost, url);
			final Document domFile = Jsoup.parse(contentPageFetcherService.fetchContentPage(fileUrl.toURI()));
			final Elements selectTotalLines = domFile.select(".mt-3 .Box-header .text-mono");

			String totalLinesAndBytes = "";

			for (final Element e : selectTotalLines) {
				totalLinesAndBytes = e.text();
				break;
			}

			if (totalLinesAndBytes.isBlank()) {
				return Optional.empty();
			}

			final GitWebCralwerItem item = new GitWebCralwerItem();
			item.setTotalLinesAndBytes(totalLinesAndBytes);
			final String fileName = url.substring(url.lastIndexOf("/") + 1);
			item.setFileName(fileName);
			item.setFileExtension(fileName.substring(fileName.lastIndexOf(".") + 1));

			return Optional.of(item);

		} catch (final Exception e) {
			LOG.error("Error fetching details file from url ={}", url, e);
			throw e;
		}

	}

}
