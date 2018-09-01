package com.pradeep.exception;

public class ValidationException extends RuntimeException {

    public ValidationException() {
        super();
    }
    public ValidationException(String s) {
        super(s);
    }
}
