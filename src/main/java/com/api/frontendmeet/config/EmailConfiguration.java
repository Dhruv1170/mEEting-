package com.api.frontendmeet.config;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfiguration {

	@Value("${spring.mail.username}")
	private String noReplyEmailId;

	@Value("${spring.mail.password}")
	private String noReplyEmailPassword;

	@Bean
	public Session getSession() {
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		// props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		// Establishing a session with required user details
		return Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				System.out.println("noReplyEmailId =======> " + noReplyEmailId);
				System.out.println("noReplyEmailPassword ======> " + noReplyEmailPassword);
				return new PasswordAuthentication(noReplyEmailId, noReplyEmailPassword);
			}
		});
	}
}
