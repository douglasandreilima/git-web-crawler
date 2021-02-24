package dev.douglas.api.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import dev.douglas.api.info.Message;

@Provider
public class CommonExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(final Exception exception) {
		// TODO logar exception
		final Status status = Status.INTERNAL_SERVER_ERROR;
		return Response.status(status.getStatusCode())
				.entity(new Message("Internal server error, contact support", status.getStatusCode())).build();
	}

}
