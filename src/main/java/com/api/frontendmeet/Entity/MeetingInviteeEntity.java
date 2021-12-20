package com.api.frontendmeet.Entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table
public class MeetingInviteeEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long meetingInviteeId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "meeting_id")
	private MeetingEntity meetingEntity;

	@Column
	Long inviteeId;

	public Long getMeetingInviteeId() {
		return meetingInviteeId;
	}

	public void setMeetingInviteeId(Long meetingInviteeId) {
		this.meetingInviteeId = meetingInviteeId;
	}

	public MeetingEntity getMeetingEntity() {
		return meetingEntity;
	}

	public void setMeetingEntity(MeetingEntity meetingEntity) {
		this.meetingEntity = meetingEntity;
	}

	public Long getInviteeId() {
		return inviteeId;
	}

	public void setInviteeId(Long inviteeId) {
		this.inviteeId = inviteeId;
	}
}