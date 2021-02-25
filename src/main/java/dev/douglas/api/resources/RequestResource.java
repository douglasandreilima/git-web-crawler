package dev.douglas.api.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dev.douglas.api.info.GitWebCralwerResponse;
import dev.douglas.api.info.GitWebCrawlerRequest;
import dev.douglas.api.services.GitWebCrawlerService;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RequestResource {

	@Inject
	GitWebCrawlerService gitWebCrawlerService;

	@GET
	public Response goToSwaggerUi() throws URISyntaxException {
		return Response.temporaryRedirect(new URI("/swagger-ui")).build();
	}

	@Path("/request")
	@POST
	public GitWebCralwerResponse request(@Valid final GitWebCrawlerRequest gitWebCrawlerRequest) throws Exception {
		return gitWebCrawlerService.calcLinesAndBytesAllFilesFromGitUrl(gitWebCrawlerRequest.getUrl());
	}

	@GET
	@Path("/request")
	public GitWebCralwerResponse request(@QueryParam("url") final String url) throws Exception {
		return gitWebCrawlerService.calcLinesAndBytesAllFilesFromGitUrl(url);
	}
}
