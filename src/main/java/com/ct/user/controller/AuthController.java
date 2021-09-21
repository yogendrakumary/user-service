package com.ct.user.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.user.model.FinalVariables;
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

	@PostMapping("/verify")
	public ResponseEntity<?> authenticate(@RequestBody UserDto user) {
		log.info("INSIDE Authenticate");

		Optional<UserDto> optional = Optional.empty();

		if (user != null && !user.getEmail().trim().isEmpty() && !user.getPassword().trim().isEmpty()) {
			optional = userServiceImpl.authenticate(user);

			if (optional.isPresent()) {
				UserDto authenticatedUser = optional.get();

				if (authenticatedUser.getAttempt() >= FinalVariables.MAX_ATTEMPT) {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authenticatedUser);
				}

				return ResponseEntity.status(HttpStatus.CREATED).body(authenticatedUser);
			}
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(Problem.create().withTitle("No User Found").withDetail("No User found with given email Id"));
	}

	@PostMapping("/forget")
	public ResponseEntity<?> forget(@RequestBody UserDto user) {
		log.info("INSIDE forget");

		if (user.getEmail().trim().isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					Problem.create().withTitle("Email Id is Blank").withDetail("Blank Email Id Not been accepted."));
		}

		Optional<UserDto> optional = userServiceImpl.resetUser(user);
		if (!optional.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Problem.create().withTitle("No User Found associated with Email ID")
							.withDetail("Email Id not matched with any of registered user."));
		}

		return new ResponseEntity<>(optional.get(), HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<?> update(@RequestBody UserDto user) {
		log.info("INSIDE update");

		Optional<UserDto> optional = userServiceImpl.updateCredentials(user);
		if (!optional.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Problem.create().withTitle("Passoword Errors").withDetail("Issue while updating password"));
		}

		return new ResponseEntity<>(optional.get(), HttpStatus.OK);
	}
}
