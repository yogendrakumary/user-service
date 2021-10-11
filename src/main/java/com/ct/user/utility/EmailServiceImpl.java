package com.ct.user.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Utility For Mail
 * 
 * @author YogendrakumarY
 *
 */
@Component
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
}
