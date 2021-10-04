package com.ct.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.user.constant.FinalVariables;
import com.ct.user.constant.Messages;
import com.ct.user.exception.auth.EmailIdNotRegisteredException;
import com.ct.user.exception.auth.PasswordMaxAttemptException;
import com.ct.user.exception.auth.PasswordNotVerifiedException;
import com.ct.user.exception.auth.UserNotFoundException;
import com.ct.user.model.AuthDto;
import com.ct.user.model.User;
import com.ct.user.model.UserDto;
import com.ct.user.model.validation.ForgetInfo;
import com.ct.user.model.validation.LoginInfo;
import com.ct.user.model.validation.UpdateInfo;
import com.ct.user.response.ResponseModel;
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
	 * @param authDto
	 * @return
	 */
	@PostMapping("/verify")
	public ResponseEntity<?> authenticate(@Validated(value = LoginInfo.class) @RequestBody AuthDto authDto) {
		log.info("INSIDE Authenticate");

		if (authDto == null) {
			throw new UserNotFoundException();
		}

		User user = userServiceImpl.getUserByEmailId(authDto.getEmail())
				.orElseThrow(EmailIdNotRegisteredException::new);

		if (user.getAttempt() >= FinalVariables.MAX_ATTEMPT) {
			throw new PasswordMaxAttemptException();
		}

		UserDto authenticatedUser = userServiceImpl.authenticate(authDto, user).orElseThrow(UserNotFoundException::new);

		if (authenticatedUser.getAttempt() >= FinalVariables.MAX_ATTEMPT || authenticatedUser.getRoleId() == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authenticatedUser);
		}

		return ResponseEntity.ok().body(authenticatedUser);
	}

	/**
	 * To Request when user forget the credentials
	 * 
	 * @param authDto
	 * @return
	 */
	@PostMapping("/forget")
	public ResponseEntity<?> forget(@Validated(value = ForgetInfo.class) @RequestBody AuthDto authDto) {
		log.info("INSIDE forget");

		if (authDto == null) {
			throw new UserNotFoundException();
		}

		User user = userServiceImpl.getUserByEmailId(authDto.getEmail())
				.orElseThrow(EmailIdNotRegisteredException::new);

		userServiceImpl.resetUser(user);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * Request to update credentials
	 * 
	 * @param authDto
	 * @return
	 */
	@PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@Validated(value = UpdateInfo.class) @RequestBody AuthDto authDto) {
		log.info("INSIDE update");

		if (authDto == null) {
			throw new UserNotFoundException();
		}

		User user = userServiceImpl.getUserByEmailId(authDto.getEmail())
				.orElseThrow(EmailIdNotRegisteredException::new);

		userServiceImpl.updateCredentials(authDto, user)
				.orElseThrow(() -> new PasswordNotVerifiedException("Issue While updating passoword"));

		return ResponseEntity.ok(new ResponseModel(Messages.PASSWORD_UPDATED_SUCCESSFULLY));
	}

	@GetMapping("/valid/{email}")
	public ResponseEntity<Boolean> validateEmail(@PathVariable String email) {
		log.info("INSIDE validateEmail");
		return ResponseEntity.ok(userServiceImpl.getUserByEmailId(email).isPresent());
	}
}
