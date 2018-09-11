package com.pradeep.exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(String s) {
		super(s);
	}
}
