package com.lookup.service.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidDriverIdExceptionMapper implements ExceptionMapper<InvalidDriverIdException> {

	@Override
	public Response toResponse(InvalidDriverIdException ex) {
		ErrorMessage msg = new ErrorMessage(ex.getMessage(), 404);
		return Response.status(Response.Status.NOT_FOUND).entity(msg)
                .type(MediaType.APPLICATION_JSON_TYPE).build();
	}

}
