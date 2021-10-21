package com.ct.user.exception.auth;

/**
 * Error With Password Verification
 * 
 * @author YogendrakumarY
 *
 */
public class PasswordNotVerifiedException extends RuntimeException{

	public PasswordNotVerifiedException() {
		super("User password error");
	}

	public PasswordNotVerifiedException(String message) {
		super(message);
	}

}
