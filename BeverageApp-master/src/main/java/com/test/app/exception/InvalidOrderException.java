package com.test.app.exception;

public class InvalidOrderException extends RuntimeException {

	private static final long serialVersionUID = 2030423614602674770L;

	// thrown when all the ingredients are excluded from an item in order
    public InvalidOrderException(String errorMessage) {
        super(errorMessage);
    }
}
