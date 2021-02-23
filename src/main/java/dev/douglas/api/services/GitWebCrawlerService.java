package dev.douglas.api.services;

import dev.douglas.api.info.GitWebCralwerResponse;

public interface GitWebCrawlerService {

	GitWebCralwerResponse calcLinesAndBytesAllFilesFromGitUrl(String url) throws Exception;
}
