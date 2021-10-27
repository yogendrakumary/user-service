package com.ct.user.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
	private long userId;

	private String title;

	private String firstName;

	private String lastName;

	private String email;

	private java.sql.Date birthDate;

	private String contactNo;

	private Integer attempt;

	private Integer roleId;

	private String roleName;

	private Integer empId;

	private String accessToken;
}
