package com.ct.user.exception.auth;

public class BlankPasswordException extends RuntimeException {

	public BlankPasswordException() {
		super("Blank Password Provided");
	}

	public BlankPasswordException(String message) {
		super(message);
	}

}
