package com.ct.user.exception;

public class PatientNotFoundException extends RuntimeException {

	public PatientNotFoundException() {
		super();
	}

	public PatientNotFoundException(long id) {
		super("Could not find patient : " + id);
	}

	public PatientNotFoundException(String arg0) {
		super(arg0);
	}

}
