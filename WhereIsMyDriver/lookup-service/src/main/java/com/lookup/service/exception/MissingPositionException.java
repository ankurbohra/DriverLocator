package com.lookup.service.exception;

public class MissingPositionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MissingPositionException(String message) {
		super(message);		
	}	
}
