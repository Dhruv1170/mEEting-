package com.api.frontendmeet.Entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table

public class MeetingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long meetingId;

	@Column(nullable = false)
	private String roomName;

	@Column(nullable = false)
	private String meetingTitle;

	@Column(nullable = false)
	private String meetingDesc;

	@Transient
	private List<String> invites;

	@Column(nullable = false)
	private LocalDateTime startDate;

	@Column(nullable = false)
	private LocalDateTime endDate;

	@CreatedDate
	@Column
	private LocalDateTime createdOn;

	@LastModifiedDate
	@Column
	private LocalDateTime modifiedOn;

	@ManyToOne()
	@JoinColumn(name = "user_id")
	private UserEntity user = new UserEntity();

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getMeetingTitle() {
		return meetingTitle;
	}

	public void setMeetingTitle(String meetingTitle) {
		this.meetingTitle = meetingTitle;
	}

	public String getMeetingDesc() {
		return meetingDesc;
	}

	public void setMeetingDesc(String meetingDesc) {
		this.meetingDesc = meetingDesc;
	}

	public List<String> getInvites() {
		return invites;
	}

	public void setInvites(List<String> invites) {
		this.invites = invites;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartTime(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndTime(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
}