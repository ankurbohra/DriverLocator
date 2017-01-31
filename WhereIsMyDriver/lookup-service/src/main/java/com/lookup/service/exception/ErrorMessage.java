package com.lookup.service.exception;

public class ErrorMessage {
	private String errrorMessage;
	private int errorCode;

	public ErrorMessage(String errrorMessage, int errorCode) {
		super();
		this.errrorMessage = errrorMessage;
		this.errorCode = errorCode;
	}

	public String getErrrorMessage() {
		return errrorMessage;
	}

	public void setErrrorMessage(String errrorMessage) {
		this.errrorMessage = errrorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
