package com.ct.user.exception.auth;

public class EmailIdAlreadyRegisteredException extends RuntimeException {

	public EmailIdAlreadyRegisteredException() {
		super("Email Id already registered");
	}

	public EmailIdAlreadyRegisteredException(String message) {
		super(message);
	}

}
