package com.api.frontendmeet.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.api.frontendmeet.Entity.UserDto;
import com.api.frontendmeet.service.RegistrationService;

@RestController
@RequestMapping("/register")
public class RegistrationController {

	public static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

	@Autowired
	RegistrationService registrationService;
	
	@GetMapping("/register")
	public String getRegistrationView() {

		return "registration";
	}

	@PostMapping("/registerUser")
	public ResponseEntity<Map<String, Object>> userRegistration(@RequestBody UserDto user) {
		try {
			logger.info("Inside userRegistration() : ");
			return new ResponseEntity<>(registrationService.saveUser(user), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error occured while registering user {} :Reason :{}", user.getEmail(), e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/verifyAccount")
	public ResponseEntity<Map<String, Object>> verifyAccount(@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password) {
		try {
			logger.info("Inside verifyAccount() : " + email);
			return new ResponseEntity<>(registrationService.verifyAccount(email, password), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error occured while registering user {} :Reason :{}");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/forgetPassword")
	public ResponseEntity<Map<String, Object>> forgetPassword(@RequestParam(value = "email") String email) {
		try {
			logger.info("Inside forgetPassword() : " + email);
			return new ResponseEntity<>(registrationService.forgetPassword(email), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error occured while registering user {} :Reason :{}");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/resetPassword")
	public ResponseEntity<Map<String, Object>> resetPassword(@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password) {
		try {
			logger.info("Inside  resetPassword() : " + email);
			return new ResponseEntity<>(registrationService.resetPassword(email, password), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error occured while registering user {} :Reason :{}");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
