package com.ct.user.exception.auth;

public class EmailIdAlreadyRegisteredException extends RuntimeException {

	public EmailIdAlreadyRegisteredException() {
		super("Email Id already Registered");
	}

	public EmailIdAlreadyRegisteredException(String message) {
		super(message);
	}

}
