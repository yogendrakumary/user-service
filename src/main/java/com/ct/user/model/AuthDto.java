package com.ct.user.model;

import javax.validation.constraints.NotBlank;

import com.ct.user.model.validation.ForgetInfo;
import com.ct.user.model.validation.LoginInfo;
import com.ct.user.model.validation.UpdateInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDto {

	@NotBlank(message = "Please enter your email address", groups = { LoginInfo.class, ForgetInfo.class })
	private String email;

	@NotBlank(message = "Please enter password", groups = { LoginInfo.class })
	private String password;

	private String oldPassword;

	@NotBlank(message = "Please enter password", groups = { UpdateInfo.class })
	private String newPassword;

}
