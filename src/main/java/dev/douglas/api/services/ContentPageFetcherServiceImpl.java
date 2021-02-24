package dev.douglas.api.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContentPageFetcherServiceImpl implements ContentPageFetcherService {

	private final HttpClient httpClient;

	public ContentPageFetcherServiceImpl() {
		httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(30)).build();
	}

	@Override
	public String fetchContentPage(final URI url) throws IOException, InterruptedException {
		final var request = HttpRequest.newBuilder(url).GET().build();
		final var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		return response.body();
	}

}
