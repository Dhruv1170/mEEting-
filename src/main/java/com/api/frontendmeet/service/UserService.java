package com.api.frontendmeet.service;

import java.util.Map;

public interface UserService {

	public Map<String, Object> editUserAPI(Long userId, String name, String password);
}
