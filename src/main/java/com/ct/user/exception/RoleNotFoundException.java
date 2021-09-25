package com.ct.user.exception;

public class RoleNotFoundException extends RuntimeException{

	public RoleNotFoundException() {
		super("Role Not Found");
	}

	public RoleNotFoundException(String message) {
		super(message);
	}
}
