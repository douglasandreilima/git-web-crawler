package dev.douglas.api.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.douglas.api.info.Message;

@Provider
public class CommonExceptionMapper implements ExceptionMapper<Exception> {

	private final static Logger LOG = LoggerFactory.getLogger(CommonExceptionMapper.class);

	@Override
	public Response toResponse(final Exception exception) {
		LOG.error("", exception);
		final Status status = Status.INTERNAL_SERVER_ERROR;
		return Response.status(status.getStatusCode())
				.entity(new Message("Internal server error, contact support", status.getStatusCode())).build();
	}

}
