package com.ct.user.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ct.user.exception.RoleNotFoundException;

import lombok.extern.java.Log;

@ResponseBody
@ControllerAdvice
@Log
public class RolesControllerAdvice {
	/**
	 * when Role not found
	 * 
	 * @param exception
	 * @return
	 */
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(RoleNotFoundException.class)
	String handlerRoleNotFoundException(RoleNotFoundException exception) {
		log.info("INSIDE userNotFoundHandler");
		return exception.getMessage();
	}
}
