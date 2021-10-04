package com.ct.user.exception.auth;

public class EmailIdNotRegisteredException extends RuntimeException{

	public EmailIdNotRegisteredException() {
		super("Email Id not registered in System");
	}

	public EmailIdNotRegisteredException(String message) {
		super(message);
	}

}
