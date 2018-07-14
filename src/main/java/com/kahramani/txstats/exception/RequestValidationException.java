package com.kahramani.txstats.exception;

public class RequestValidationException extends RuntimeException {

    private final Object[] arguments;

    public RequestValidationException(String message, Object... arguments) {
        super(message);
        this.arguments = arguments;
    }

    Object[] getArguments() {
        return arguments;
    }
}
