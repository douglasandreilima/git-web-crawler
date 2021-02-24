package dev.douglas.api.services;

import java.io.IOException;
import java.net.URI;

public interface ContentPageFetcherService {

	String fetchContentPage(URI url) throws IOException, InterruptedException;
}
