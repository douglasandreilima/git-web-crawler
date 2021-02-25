package dev.douglas.api.exceptionmapper;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.douglas.api.info.Message;

@Provider
public class NotFoundExeptionMapper implements ExceptionMapper<NotFoundException> {

	private final static Logger LOG = LoggerFactory.getLogger(NotFoundExeptionMapper.class);

	@Override
	public Response toResponse(final NotFoundException exception) {

		try {
			return Response.temporaryRedirect(new URI("/swagger-ui")).build();
		} catch (final URISyntaxException e) {
			LOG.error("", e);
			final Status status = Status.NOT_FOUND;
			return Response.status(status.getStatusCode())
					.entity(new Message("resource not found", status.getStatusCode())).build();
		}
	}

}
