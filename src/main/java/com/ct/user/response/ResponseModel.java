package com.ct.user.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseModel {

	public ResponseModel(String message) {
		super();
		this.message = message;
	}

	private String message;
}
