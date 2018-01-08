package com.impetus.bookstore.exception;

public class ServiceLayerException extends Exception {

	private static final long serialVersionUID = 1L;

	String message;

	public ServiceLayerException(String message) {
		this.message = message;
	}
}