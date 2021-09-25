package com.ct.user.exception.auth;

public class PasswordMaxAttemptException extends RuntimeException{

	public PasswordMaxAttemptException() {
		super("Password Max Attempt Reached.");
		// TODO Auto-generated constructor stub
	}

	public PasswordMaxAttemptException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
