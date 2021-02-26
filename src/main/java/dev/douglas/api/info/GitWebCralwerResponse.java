package dev.douglas.api.info;

import java.util.List;
import java.util.Map;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class GitWebCralwerResponse {

	private String urlRepository;

	private String message;

	private Map<String, List<GitWebCralwerItem>> files;

	public GitWebCralwerResponse() {
	}

	public static GitWebCralwerResponse newInstance(final String message) {
		final GitWebCralwerResponse response = new GitWebCralwerResponse();
		response.setMessage(message);

		return response;
	}

	public String getUrlRepository() {
		return urlRepository;
	}

	public void setUrlRepository(final String urlRepository) {
		this.urlRepository = urlRepository;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public Map<String, List<GitWebCralwerItem>> getFiles() {
		return files;
	}

	public void setFiles(final Map<String, List<GitWebCralwerItem>> files) {
		this.files = files;
	}

}
