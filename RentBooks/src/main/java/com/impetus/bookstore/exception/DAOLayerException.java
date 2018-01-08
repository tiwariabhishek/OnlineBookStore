package com.impetus.bookstore.exception;

public class DAOLayerException extends Exception {

	private static final long serialVersionUID = 1L;

	String message;

	public DAOLayerException(String message) {
		this.message = message;
	}
}
