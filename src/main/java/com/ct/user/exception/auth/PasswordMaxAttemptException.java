package com.ct.user.exception.auth;

public class PasswordMaxAttemptException extends RuntimeException {

	public PasswordMaxAttemptException() {
		super("Password Max Attempt Reached.");
	}

	public PasswordMaxAttemptException(String message) {
		super(message);
	}

}
