package com.ct.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.ct.user.model.validation.EmployeeInfo;
import com.ct.user.model.validation.PatientInfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDto {

	private long userId;

	@NotBlank(message = "Title Should not be Null", groups = { PatientInfo.class, EmployeeInfo.class })
	private String title;

	@NotBlank(message = "First name cannot be empty.", groups = { PatientInfo.class, EmployeeInfo.class })
	private String firstName;

	@NotBlank(message = "Last name cannot be empty.", groups = { PatientInfo.class, EmployeeInfo.class })
	private String lastName;

	@NotBlank(message = "Please enter your email address e.g. exampleusername@xyzdomain.com", groups = {
			PatientInfo.class, EmployeeInfo.class })
	private String email;

	@NotNull(message = "Please enter a valid date", groups = { PatientInfo.class, EmployeeInfo.class })
	private java.sql.Date birthDate;

	private String contactNo;

	@NotBlank(message = "Password should not be blank", groups = { PatientInfo.class })
	private String password;

	private Integer attempt;

	private boolean deleted;

	private String status;

//	private String username;

	@NotNull(message = "Please select a valid role.", groups = { EmployeeInfo.class })
	private Integer roleId;

	private String roleName;

	private Role role;
	private Integer empId;

	private String race;
	private String ethnicity;
	private String languages;
	private String address;

	private String oldPassword;
	private String newPassword;

	private java.util.Date createdOn;
	private java.util.Date updatedOn;
	
	private String message;

}
