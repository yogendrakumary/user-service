package com.ct.user.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.user.exception.auth.EmailIdNotRegisteredException;
import com.ct.user.exception.auth.PasswordMaxAttemptException;
import com.ct.user.exception.auth.BlankPasswordException;
import com.ct.user.exception.auth.PasswordNotVerifiedException;
import com.ct.user.exception.auth.UserNotFoundException;
import com.ct.user.model.FinalVariables;
import com.ct.user.model.User;
import com.ct.user.model.UserDto;
import com.ct.user.service.UserService;

import lombok.extern.java.Log;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
@Log
public class AuthController {

	@Autowired
	private UserService userServiceImpl;

	/**
	 * User Verification with email and password
	 * 
	 * @param userDto
	 * @return
	 */
	@PostMapping("/verify")
	public ResponseEntity<?> authenticate(@RequestBody UserDto userDto) {
		log.info("INSIDE Authenticate");

		if (userDto == null) {
			throw new UserNotFoundException();
		}

		if (userDto.getPassword() == null || userDto.getPassword().trim().isEmpty()) {
			throw new BlankPasswordException();
		}

		User user = userServiceImpl.getUserByEmailId(userDto.getEmail())
				.orElseThrow(EmailIdNotRegisteredException::new);

		if (user.getAttempt() >= FinalVariables.MAX_ATTEMPT) {
			throw new PasswordMaxAttemptException();
		}

		Optional<UserDto> optional = userServiceImpl.authenticate(userDto, user);

		if (optional.isPresent()) {
			UserDto authenticatedUser = optional.get();

			if (authenticatedUser.getAttempt() >= FinalVariables.MAX_ATTEMPT || authenticatedUser.getRoleId() == 0) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authenticatedUser);
			}

			return ResponseEntity.ok().body(authenticatedUser);
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No User found with given email Id");
	}

	/**
	 * To Request when user forget the credentials
	 * 
	 * @param userDto
	 * @return
	 */
	@PostMapping("/forget")
	public ResponseEntity<?> forget(@RequestBody UserDto userDto) {
		log.info("INSIDE forget");

		if (userDto == null) {
			throw new UserNotFoundException();
		}

		User user = userServiceImpl.getUserByEmailId(userDto.getEmail())
				.orElseThrow(EmailIdNotRegisteredException::new);

		userServiceImpl.resetUser(user);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * Request to update credentials
	 * 
	 * @param userDto
	 * @return
	 */
	@PostMapping("/update")
	public ResponseEntity<?> update(@RequestBody UserDto userDto) {
		log.info("INSIDE update");

		if (userDto == null) {
			throw new UserNotFoundException();
		}

		if (userDto.getNewPassword() == null || userDto.getNewPassword().trim().isEmpty())
			throw new BlankPasswordException();

		User user = userServiceImpl.getUserByEmailId(userDto.getEmail())
				.orElseThrow(EmailIdNotRegisteredException::new);

		Optional<UserDto> optional = userServiceImpl.updateCredentials(userDto, user);

		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		}

		return ResponseEntity.badRequest().body("Issue While updating passoword");
	}
}
