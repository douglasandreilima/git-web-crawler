package dev.douglas.api.info;

import javax.validation.constraints.NotNull;

public class GitWebCrawlerRequest {

	@NotNull
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

}
