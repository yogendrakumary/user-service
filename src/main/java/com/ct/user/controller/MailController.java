package com.ct.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.user.model.Mail;
import com.ct.user.utility.EmailServiceImpl;

import lombok.extern.java.Log;

@RestController
@Log
@RequestMapping("/mail")
public class MailController {

	@Autowired
	private EmailServiceImpl emailServiceImpl;

	/**
	 * To send mail from configured
	 * 
	 * @param mail
	 * @return
	 */
	@PostMapping("/send")
	public ResponseEntity<?> sendMail(@RequestBody Mail mail) {
		log.info("INSIDE sendMail");
		emailServiceImpl.sendSimpleMessage(mail.getTo(), mail.getSubject(), mail.getBody());
		return ResponseEntity.ok(true);
	}
	@PostMapping("/sendmultiplemail")
	public ResponseEntity<?> sendMultipleMail(@RequestBody Mail mail) {
		log.info("INSIDE sendMultipleMail");
		try {
			emailServiceImpl.sendMultipleMessage( mail.getSubject(), mail.getBody(),mail.getMultipleTo());
		return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
