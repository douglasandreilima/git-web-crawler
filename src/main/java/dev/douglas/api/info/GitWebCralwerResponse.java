package dev.douglas.api.info;

import java.util.List;

public class GitWebCralwerResponse {

	private String urlRepository;

	private List<GitWebCralwerItem> files;

	public String getUrlRepository() {
		return urlRepository;
	}

	public void setUrlRepository(final String urlRepository) {
		this.urlRepository = urlRepository;
	}

	public List<GitWebCralwerItem> getFiles() {
		return files;
	}

	public void setFiles(final List<GitWebCralwerItem> files) {
		this.files = files;
	}

}
