package com.lookup.service.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class MissingPositionExceptionMapper implements
		ExceptionMapper<MissingPositionException> {

	@Override
	public Response toResponse(MissingPositionException ex) {
		ErrorMessage msg = new ErrorMessage(ex.getMessage(), 400);
		return Response.status(Status.BAD_REQUEST).entity(msg)
				.type(MediaType.APPLICATION_JSON_TYPE).build();
	}

}
