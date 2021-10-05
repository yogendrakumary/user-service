package com.ct.user.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ct.user.exception.PatientNotFoundException;

import lombok.extern.java.Log;

@ControllerAdvice
@ResponseBody
@Log
public class PatientControllerAdvice {

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(PatientNotFoundException.class)
	String patientNotFoundHandler(PatientNotFoundException exception) {
		log.info("INSIDE patientNotFoundHandler");
		return exception.getMessage();
	}

}
