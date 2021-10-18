package com.ct.user.exception.auth;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		super("User not found in system");
	}

	public UserNotFoundException(String message) {
		super(message);
	}

}
