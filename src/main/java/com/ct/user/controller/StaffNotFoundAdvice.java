package com.ct.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ct.user.exception.StaffNotFoundException;

import lombok.extern.java.Log;

/**
 * Controller Advice to Handle the Exception in Staff Controller
 * 
 * @author YogendrakumarY
 */
@ControllerAdvice
@ResponseBody
@Log
public class StaffNotFoundAdvice {

	/**
	 * when Staff not found
	 * 
	 * @param exception
	 * @return
	 */
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(StaffNotFoundException.class)
	String staffNotFoundHandler(StaffNotFoundException exception) {
		log.info("INSIDE staffNotFoundHandler");
		return exception.getMessage();
	}

}
