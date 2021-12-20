package com.api.frontendmeet.service;

import java.util.Map;

import com.api.frontendmeet.Entity.UserDto;
import com.api.frontendmeet.Entity.UserEntity;

public interface RegistrationService {

	public Map<String, Object> saveUser(UserDto userDto);

	public Map<String, Object> verifyAccount(String email, String password);

	public UserEntity populateUserData(UserDto userDto);

	public Map<String, Object> forgetPassword(String email);

	public Map<String, Object> resetPassword(String email, String password);

}
