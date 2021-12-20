package com.api.frontendmeet.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetingDto {

	private MeetingEntity meetingEntity;

	MeetingDto() {

	}

	public MeetingEntity getMeetingEntity() {
		return meetingEntity;
	}

	public void setMeetingEntity(MeetingEntity meetingEntity) {
		this.meetingEntity = meetingEntity;
	}

}
