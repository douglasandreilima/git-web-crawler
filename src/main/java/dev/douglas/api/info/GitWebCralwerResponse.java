package dev.douglas.api.info;

import java.util.List;
import java.util.Map;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class GitWebCralwerResponse {

	private String urlRepository;

	private Map<String, List<GitWebCralwerItem>> files;

	public String getUrlRepository() {
		return urlRepository;
	}

	public void setUrlRepository(final String urlRepository) {
		this.urlRepository = urlRepository;
	}

	public Map<String, List<GitWebCralwerItem>> getFiles() {
		return files;
	}

	public void setFiles(final Map<String, List<GitWebCralwerItem>> files) {
		this.files = files;
	}

}
