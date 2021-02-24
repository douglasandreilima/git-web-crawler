package dev.douglas.api.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.douglas.api.info.GitWebCralwerItem;
import dev.douglas.api.info.GitWebCralwerResponse;
import dev.douglas.api.validators.GitUrlFileValidator;
import dev.douglas.api.validators.GitUrlValidator;

@ApplicationScoped
public class GitWebCrawlerServiceImpl implements GitWebCrawlerService {

	private final static Logger LOG = LoggerFactory.getLogger(GitWebCrawlerServiceImpl.class);

	@Inject
	GitUrlValidator gitUrlValidator;

	@Inject
	GitUrlFileValidator gitUrlFileValidator;

	@Inject
	ContentPageFetcherService contentPageFetcherService;

	@Override
	public GitWebCralwerResponse calcLinesAndBytesAllFilesFromGitUrl(final String urlRequest) throws Exception {

		try {
			final URL url = new URL(urlRequest);
			if (!gitUrlValidator.test(url)) {
				throw new BadRequestException("Invalid parameter URL");
			}

			final Set<String> filesLinks = fetchAllFilesLinks(url);

			final GitWebCralwerResponse response = new GitWebCralwerResponse();
			response.setUrlRepository(urlRequest);
			response.setFiles(fetchFileItems(url, filesLinks));

			return response;

		} catch (final MalformedURLException e) {
			throw new BadRequestException("Invalid parameter URL");
		}
	}

	private Set<String> fetchAllFilesLinks(final URL url) throws IOException, InterruptedException, URISyntaxException {

		final Set<String> filesLinks = new HashSet<>();
		final Queue<URI> links = new LinkedList<>();
		links.add(url.toURI());
		do {

			final Document dom = Jsoup.parse(contentPageFetcherService.fetchContentPage(links.poll()));
			final Elements select = dom.select(".js-details-container .js-navigation-container .link-gray-dark");

			for (final Element element : select) {

				final String urlPath = element.attr("href");
				if (gitUrlFileValidator.test(urlPath)) {
					filesLinks.add(urlPath);
				} else {
					final URL linkUrl = new URL(url, urlPath);
					links.add(linkUrl.toURI());
				}
			}

		} while (!links.isEmpty());

		return filesLinks;

	}

	private Map<String, List<GitWebCralwerItem>> fetchFileItems(final URL urlHost, final Set<String> linksFiles) {

		final Map<String, List<GitWebCralwerItem>> fileItems = new HashMap<>();

		linksFiles.forEach(l -> {
			try {

				final URL fileUrl = new URL(urlHost, l);
				final Document domFile = Jsoup.parse(contentPageFetcherService.fetchContentPage(fileUrl.toURI()));
				final Elements selectTotalLines = domFile.select(".Box-header .text-mono");

				final GitWebCralwerItem item = new GitWebCralwerItem();
				final String fileName = l.substring(l.lastIndexOf("/") + 1);
				item.setFileName(fileName);
				final String fileExtension = fileName.substring(fileName.lastIndexOf("."));

				selectTotalLines.forEach(e -> {
					if (e.text().contains("line")) {
						item.setTotalLinesAndBytes(e.text());
					}
				});

				List<GitWebCralwerItem> listItems = fileItems.get(fileExtension);
				if (listItems == null) {
					listItems = new ArrayList<>();
					fileItems.put(fileExtension, listItems);
				}
				listItems.add(item);

			} catch (final Exception e1) {
				LOG.error(String.format("Error fetching details file from url=%s"), e1);
			}
		});

		return fileItems;
	}

}
