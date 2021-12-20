package com.api.frontendmeet.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.frontendmeet.Entity.UserEntity;
import com.api.frontendmeet.constant.ApplicationConstant;
import com.api.frontendmeet.repository.UserRepository;
import com.api.frontendmeet.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Map<String, Object> editUserAPI(Long userId, String name, String password) {
		// TODO Auto-generated method stub
		Optional<UserEntity> userEntity = userRepository.findById(userId);
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			if (userEntity != null) {
				UserEntity user = userEntity.get();
				user.setName(name);
				if (!user.getPassword().equalsIgnoreCase(password)) {
					user.setPassword(passwordEncoder.encode(password));
				}
				userRepository.save(user);
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.USER_EDIT_SUCCESS);
				map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
			}
		} catch (Exception e) {
			// TODO: handle exception
			map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
			map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
		}
		return map;
	}
}
