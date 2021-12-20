package com.api.frontendmeet.service;

import java.util.Date;
import java.util.Map;

import com.api.frontendmeet.Entity.MeetingDto;
import com.api.frontendmeet.Entity.MeetingEntity;
import com.api.frontendmeet.Entity.UserEntity;


public interface MeetingService {

	public Map<String, Object> saveMeetingAPI(MeetingDto meetingDto);

	public Map<String, Object> deleteMeetingAPI(Long meetingId);

	public Map<String, Object> editMeetingAPI(MeetingDto meetingDto);

	public UserEntity populateGuestUserData(String invitee);

	public MeetingEntity getByMeetingId(Long meetingId);

	public Map<String, Object> getMeetingByDateAPI(Date meetingDate, Integer userId, String searchText);

	public Map<String, Object> validateRoom(String roomName);
	
	  //MeetingEntity saveMeeting(MeetingDto meetingDto);
	
	
}
