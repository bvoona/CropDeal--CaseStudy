package com.cropdeal.dealerservice.exception;

public class DealerNotFoundException extends Exception {

	private String message;

	public DealerNotFoundException() {

	}

	public DealerNotFoundException(String message) {
		super(message);
		this.message = message;
	}
}
