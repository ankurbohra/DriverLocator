package com.lookup.service.exception;

public class InvalidDriverIdException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidDriverIdException(String message) {
		super(message);		
	}

}
