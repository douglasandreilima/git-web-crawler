package dev.douglas.api.services;

import java.net.MalformedURLException;
import java.net.URL;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

import dev.douglas.api.info.GitWebCralwerResponse;
import dev.douglas.api.validators.GitUrlValidator;

@ApplicationScoped
public class GitWebCrawlerServiceImpl implements GitWebCrawlerService {

	@Inject
	GitUrlValidator gitUrlValidator;

	@Override
	public GitWebCralwerResponse calcLinesAndBytesAllFilesFromGitUrl(final String url) throws Exception {

		try {
			if (!gitUrlValidator.test(new URL(url))) {

			}

		} catch (final MalformedURLException e) {
			throw new BadRequestException("Invalid parameter URL");
		}

		return null;
	}

}
