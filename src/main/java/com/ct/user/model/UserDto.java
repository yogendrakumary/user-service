package com.ct.user.model;

import java.util.Date;

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

	private Date birthDate;
	private long contactNo;
	private Integer attempt;

	private int roleId;
	private int empId;

	private String password;
	private String oldPassword;
	private String newPassword;
}
