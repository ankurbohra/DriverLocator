package com.lookup.service.core;

public class InvalidPositionException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InvalidPositionException(String s) {
		super(s);
	}
}
