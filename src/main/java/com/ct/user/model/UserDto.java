package com.ct.user.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDto {

	private long userId;
	private String title;
	private String firstName;
	private String lastName;
	private String email;
	private String username;

	private java.sql.Date birthDate;
	private String contactNo;
	private Integer attempt;

	private int roleId;
	private String roleName;
	private int empId;

	private String race;
	private String ethnicity;
	private String languages;
	private String address;

	private String password;
	private String oldPassword;
	private String newPassword;

	private java.util.Date createdOn;
	private java.util.Date updatedOn;
}
