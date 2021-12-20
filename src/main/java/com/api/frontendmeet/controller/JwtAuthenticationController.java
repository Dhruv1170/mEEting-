package com.api.frontendmeet.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.frontendmeet.Entity.JwtRequest;
import com.api.frontendmeet.Entity.UserEntity;
import com.api.frontendmeet.config.JwtTokenUtil;
import com.api.frontendmeet.constant.ApplicationConstant;
import com.api.frontendmeet.repository.UserRepository;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	@Qualifier("theJwtTokenUtil")
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapResponse = new HashMap<String, Object>();
		Map<String, Object> mapResponseUserData = new HashMap<String, Object>();
		try {
			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

			final UserDetails userDetails = jwtInMemoryUserDetailsService
					.loadUserByUsername(authenticationRequest.getUsername());
			final String token = jwtTokenUtil.generateToken(userDetails);
			mapResponse.put("token", token);

			Optional<UserEntity> userEntity = userRepository.findOneByEmailIgnoreCase(userDetails.getUsername());

			if (userEntity.isPresent()) {
				UserEntity userData = userEntity.get();

				if (userData.getIsVerified() == Boolean.FALSE) {

					map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
					map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.LOGIN_NOT_VERIFIED);
					map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
				}
				mapResponseUserData.put("id", userData.getId().toString());
				mapResponseUserData.put("name", userData.getName());
				mapResponseUserData.put("email", userData.getEmail());
				mapResponseUserData.put("token", token);
				mapResponse.put("userData", mapResponseUserData);
				if (userData.getIsVerified() != null && userData.getIsVerified().equals(true)) {
					map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
					map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.LOGIN_SUCCESS);
					map.put(ApplicationConstant.RESPONSE_DATA, mapResponseUserData);
				}
			}
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			if (e.getMessage().equalsIgnoreCase("INVALID_CREDENTIALS")) {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.LOGIN_INVALID_CREDENTIALS);
				map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
			}
		}
		map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
		map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.LOGIN_INVALID_CREDENTIALS);
		map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
		return ResponseEntity.ok(map);
	}

	private void authenticate(String name, String password) throws Exception {
		Objects.requireNonNull(name);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception(ApplicationConstant.LOGIN_INVALID_CREDENTIALS, e);
		}
	}

}
