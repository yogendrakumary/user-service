package com.ct.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.user.utility.EmailServiceImpl;

import lombok.extern.java.Log;

@RestController
@Log
public class MailController {

	@Autowired
	private EmailServiceImpl emailServiceImpl;

	@GetMapping("/mail")
	public ResponseEntity<String> sendMail() {
		log.info("INSIDE mail");
		emailServiceImpl.sendSimpleMessage("yyogendra06@gmail.com", "SUBJECT", "THIS IS BODY");
		return ResponseEntity.ok("Send Successfully");
	}
}
