package com.ct.user.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility For Mail
 * 
 * @author YogendrakumarY
 *
 */
@Component
@Slf4j
public class EmailServiceImpl {
	
	@Autowired
	private JavaMailSender emailSender;

	/**
	 * Send Mail
	 * 
	 * @param to
	 * @param subject
	 * @param text
	 */
	public void sendSimpleMessage(String to, String subject, String text) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("noreply@ctgeneralhospital.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}
	

	public void sendMultipleMessage(String subject, String body, String[] multipleTo) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("noreply@ctgeneralhospital.com");
		message.setTo(multipleTo);
		message.setSubject(subject);
		message.setText(body);
		emailSender.send(message);
		
	}
}
