package com.webapp.exceptions;

public class TheatreAlreadyExistsException extends RuntimeException {
    public TheatreAlreadyExistsException(String message) {
        super(message);
    }
}
