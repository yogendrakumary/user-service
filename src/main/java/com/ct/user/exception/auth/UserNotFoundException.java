package com.ct.user.exception.auth;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		super("User Not Found in System");
	}

	public UserNotFoundException(String message) {
		super(message);
	}

}
