package com.ct.user.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.user.model.User;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

	@PostMapping("/forget/{email}")
	public String forget(@PathVariable String email) {
//		System.out.println(user.getEmail());
		return email;
	}

	@PostMapping("/update")
	public String update(@RequestBody User user) {
		System.out.println(user.getEmail());
		return user.getEmail();
	}
}
