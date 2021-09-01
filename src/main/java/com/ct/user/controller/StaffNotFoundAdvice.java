package com.ct.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ct.user.exception.StaffNotFoundException;

@ControllerAdvice
public class StaffNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(StaffNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	String StaffNotFound(StaffNotFoundException exception) {
		return exception.getMessage();
	}
}
