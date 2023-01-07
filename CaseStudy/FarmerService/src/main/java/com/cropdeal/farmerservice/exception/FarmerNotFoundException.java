package com.cropdeal.farmerservice.exception;

public class FarmerNotFoundException extends Exception {
	private String message;

	public FarmerNotFoundException(String message) {
		super(message);
		this.message = message;
	}

}
