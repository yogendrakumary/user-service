package com.ct.user.exception.auth;

public class IncorrectPasswordException extends RuntimeException {

	public IncorrectPasswordException() {
		super("Incorrect Passoword");
	}

	public IncorrectPasswordException(String message) {
		super(message);
	}

}
