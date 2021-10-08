package com.ct.user.exception.auth;

public class PasswordMaxAttemptException extends RuntimeException {

	public PasswordMaxAttemptException() {
		super("Your account has been locked. Please contact the hospital administrator or call helpdesk on 123456 for more information.");
	}

	public PasswordMaxAttemptException(String message) {
		super(message);
	}

}
