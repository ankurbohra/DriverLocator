package com.lookup.service.exception;

public class GenericRunTimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GenericRunTimeException(String msg, Throwable t) {
		super(msg, t);
	}

	public GenericRunTimeException(Throwable t) {
		super(t);
	}
}