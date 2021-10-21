package com.ct.user.response;

import com.ct.user.model.UserDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtTokenResponse {
	private UserDto user;
	private String token;
}
