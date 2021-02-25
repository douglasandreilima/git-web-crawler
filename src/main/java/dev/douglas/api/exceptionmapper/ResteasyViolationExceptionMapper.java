package dev.douglas.api.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.api.validation.ResteasyViolationException;

import dev.douglas.api.info.Message;

@Provider
public class ResteasyViolationExceptionMapper implements ExceptionMapper<ResteasyViolationException> {

	@Override
	public Response toResponse(final ResteasyViolationException exception) {

		final Status status = Status.BAD_REQUEST;
		return Response.status(status.getStatusCode())
				.entity(new Message(exception.getMessage(), status.getStatusCode())).build();
	}

}
