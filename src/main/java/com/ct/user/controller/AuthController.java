package com.ct.user.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		System.out.println("INSIDE authenticate ");
		System.out.println(user);
		Optional<UserDto> optional = userServiceImpl.authenticate(user);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Problem.create().withTitle("Invalid Login").withDetail("Email or Passoword is Mismatch"));
		}

		UserDto authenticatedUser = optional.get();
		System.out.println(authenticatedUser);
		return ResponseEntity.created(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(AuthController.class).authenticate(authenticatedUser)).toUri())
				.body(authenticatedUser);
	}

	@PostMapping("/forget")
	public ResponseEntity<?> forget(@RequestBody UserDto user) {
		System.out.println(user);

		if (user.getEmail().isBlank()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					Problem.create().withTitle("Email Id is Blank").withDetail("Blank Email Id Not been accepted."));
		}

		Optional<UserDto> optional = userServiceImpl.resetUser(user);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Problem.create().withTitle("No User Found associated with Email ID")
							.withDetail("Email Id not matched with any of registered user."));
		}

		return new ResponseEntity<>(optional.get(), HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<?> update(@RequestBody UserDto user) {
		log.info("INSIDE UPDATE");
		log.info("User : " + user);

		Optional<UserDto> optional = userServiceImpl.updateCredentials(user);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Problem.create().withTitle("Passoword Errors").withDetail("Issue while updating password"));
		}

		return new ResponseEntity<>(optional.get(), HttpStatus.OK);
	}
}
