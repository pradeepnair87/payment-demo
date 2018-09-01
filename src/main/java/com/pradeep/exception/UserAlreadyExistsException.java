package com.pradeep.exception;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException() {
        super();
    }
    public UserAlreadyExistsException(String s) {
        super(s);
    }
}
