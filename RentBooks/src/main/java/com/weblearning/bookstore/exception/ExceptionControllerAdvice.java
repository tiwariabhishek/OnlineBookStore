package com.weblearning.bookstore.exception;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

	private static final Logger LOGGER = Logger
			.getLogger(ExceptionControllerAdvice.class);

	@ExceptionHandler({ Exception.class })
	public String handleException(Exception e) {
		LOGGER.error("Caught an unhandled exception", e);
		return "exception";
	}

	@ExceptionHandler({ RuntimeException.class })
	public String handleRuntimeException(Exception e) {
		LOGGER.error("Caught an runtime exception", e);
		return "exception";
	}
}
