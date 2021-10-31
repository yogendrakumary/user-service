package com.ct.user.controller.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ct.user.exception.auth.BlankPasswordException;
import com.ct.user.exception.auth.EmailIdAlreadyRegisteredException;
import com.ct.user.exception.auth.EmailIdNotRegisteredException;
import com.ct.user.exception.auth.IncorrectPasswordException;
import com.ct.user.exception.auth.PasswordMaxAttemptException;
import com.ct.user.exception.auth.PasswordNotVerifiedException;
import com.ct.user.exception.auth.UserNotFoundException;

import lombok.extern.java.Log;

@ControllerAdvice
@ResponseBody
@Log
public class UserControllerAdvice {

	/**
	 * when User not found
	 * 
	 * @param exception
	 * @return
	 */
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(UserNotFoundException.class)
	String userNotFoundHandler(UserNotFoundException exception) {
		log.info("INSIDE userNotFoundHandler");
		return exception.getMessage();
	}

	/**
	 * Catch the exception on validation of entity while in controller
	 * 
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		log.info("INSIDE validation handleValidationExceptions");
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	/**
	 * While updating password exception is handled here
	 * 
	 * @param ex
	 * @return
	 */
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(PasswordNotVerifiedException.class)
	public String handlerPasswordException(PasswordNotVerifiedException ex) {
		return ex.getMessage();
	}

	/**
	 * Password Not Found
	 * 
	 * @param ex
	 * @return
	 */
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(BlankPasswordException.class)
	public String handlerPasswordNotFoundException(BlankPasswordException ex) {
		return ex.getMessage();
	}
	
	/**
	 * Incorrect Password Exception
	 * 
	 * @param ex
	 * @return
	 */
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(IncorrectPasswordException.class)
	public String handlerIncorrectPasswordException(IncorrectPasswordException ex) {
		return ex.getMessage();
	}

	/**
	 * Password Max Attempt Reached
	 * 
	 * @param ex
	 * @return
	 */
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(PasswordMaxAttemptException.class)
	public String handlerPasswordMaxAttemptException(PasswordMaxAttemptException ex) {
		return ex.getMessage();
	}

	/**
	 * Email Not Found
	 * 
	 * @param ex
	 * @return
	 */
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(EmailIdNotRegisteredException.class)
	public String handlerEmailIdNotFoundException(EmailIdNotRegisteredException ex) {
		return ex.getMessage();
	}

	/**
	 * User Already Registered with Email ID
	 * 
	 * @param exception
	 * @return
	 */
	@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler(EmailIdAlreadyRegisteredException.class)
	String handlerEmailIdAlreadyRegisteredException(EmailIdAlreadyRegisteredException exception) {
		log.info("INSIDE handlerEmailIdAlreadyRegisteredException");
		return exception.getMessage();
	}

}
