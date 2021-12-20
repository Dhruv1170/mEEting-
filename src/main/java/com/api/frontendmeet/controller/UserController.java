package com.api.frontendmeet.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.api.frontendmeet.service.UserService;



@RestController
@RequestMapping("/user")
public class UserController {

public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	@PostMapping("/editUserAPI")
	public ResponseEntity<Map<String, Object>> editUserAPI(@RequestParam("userId") Long userId, 
			@RequestParam("name") String name, @RequestParam("password") String password) {
		try {
			logger.info("editUser : " + userId);
			return new ResponseEntity<>(userService.editUserAPI(userId, name, password), HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error occured while editUserAPI {} :Reason :{}",
					name,
					e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
}
