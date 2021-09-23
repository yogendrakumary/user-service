package com.ct.user.exception;

public class StaffNotFoundException extends RuntimeException {

	public StaffNotFoundException() {
		super();
	}

	public StaffNotFoundException(String string) {
		super(string);
	}

	public StaffNotFoundException(long id) {
		super("Staff Not find by Id : " + id);
	}
}
