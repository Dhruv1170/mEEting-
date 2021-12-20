package com.api.frontendmeet.service;

import com.api.frontendmeet.Entity.MeetingDto;
import com.api.frontendmeet.Entity.UserDto;
import com.api.frontendmeet.Entity.UserEntity;

public interface EmailService {

	public void sendWelcomeMailToUser(UserDto userDto);

	public void sendInvitationMailToUser(String inviteeemail, Long meetingId, Long userId, MeetingDto meetingDto,
			String email1);

	public void sendPasswordResetMailToUser(UserEntity userDto);

}