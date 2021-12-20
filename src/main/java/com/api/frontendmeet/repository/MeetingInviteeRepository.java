package com.api.frontendmeet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.api.frontendmeet.Entity.MeetingInviteeEntity;



public interface MeetingInviteeRepository extends JpaRepository<MeetingInviteeEntity, Long> {

	@Transactional
	@Modifying
	@Query("DELETE FROM MeetingInviteeEntity m WHERE m.meetingEntity.meetingId = :id")
	void deleteFromInvitee(Long id);

	@Query("SELECT m FROM MeetingInviteeEntity m WHERE m.meetingEntity.meetingId = :meetingId")
	List<MeetingInviteeEntity> findbyMeetingId(Long meetingId);

	@Transactional
	@Modifying  
	@Query(value = "INSERT INTO meeting_invitee_entity (meeting_id, invitee_id) VALUES (:meetingId, :inviteeId)", nativeQuery = true)
	void addMeetingInvitee(Long meetingId, Long inviteeId);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM MeetingInviteeEntity m WHERE m.inviteeId = :inviteeId")
	void deleteUserFromInvitee(Long inviteeId);
	
	@Query("SELECT m FROM MeetingInviteeEntity m WHERE m.inviteeId = :userId")
	List<MeetingInviteeEntity> findbyInvaiteeId(Long userId);

}
