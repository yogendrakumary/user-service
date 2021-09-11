package com.ct.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.user.model.User;
import com.ct.user.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping("/verify")
	public ResponseEntity<?> authenticate(@RequestBody User user) {
		System.out.println("INSIDE authenticate ");
		System.out.println(user);
		User authenticatedUser = userService.authenicate(user);
		System.out.println(authenticatedUser);
		return ResponseEntity.created(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(AuthController.class).authenticate(authenticatedUser)).toUri())
				.body(authenticatedUser);
	}

	@PostMapping("/forget/{email}")
	public ResponseEntity<?> forget(@PathVariable String email) {
		System.out.println(email);
		return new ResponseEntity<>(new User(), HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<?> update(@RequestBody User user) {
		System.out.println("INSIDE UPDATE");
		System.out.println(user);
		return ResponseEntity.created(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(AuthController.class).update(user)).withSelfRel().toUri())
				.body(user);
	}
}
