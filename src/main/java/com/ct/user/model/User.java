package com.ct.user.model;

import lombok.Data;

@Data
public class User {
	private String title;
	private String firstName;
	private String lastName;
	private String email;
	private int roleId;
}
