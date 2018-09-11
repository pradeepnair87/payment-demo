package com.pradeep.exception;

public class InsufficentBalanceException extends RuntimeException {

	public InsufficentBalanceException() {
		super();
	}

	public InsufficentBalanceException(String s) {
		super(s);
	}
}