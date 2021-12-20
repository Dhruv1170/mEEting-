package com.api.frontendmeet.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.api.frontendmeet.Entity.MeetingDto;
import com.api.frontendmeet.Entity.MeetingEntity;
import com.api.frontendmeet.Entity.MeetingInviteeEntity;
import com.api.frontendmeet.Entity.UserEntity;
import com.api.frontendmeet.constant.ApplicationConstant;
import com.api.frontendmeet.repository.MeetingInviteeRepository;
import com.api.frontendmeet.repository.MeetingRepository;
import com.api.frontendmeet.repository.UserRepository;
import com.api.frontendmeet.service.EmailService;
import com.api.frontendmeet.service.MeetingService;

@Service
public class MeetingSeviceImpl implements MeetingService {

	@Autowired
	private MeetingRepository meetingRepository;

	@Autowired
	private MeetingInviteeRepository meetingInviteeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;

	@Override
	public Map<String, Object> saveMeetingAPI(MeetingDto meetingDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> emails = new ArrayList<String>();

		if (meetingDto != null) {
			MeetingEntity meetingEntity = populateMeetingData(meetingDto);
			MeetingEntity DbMeetingEntity = meetingRepository.save(meetingEntity);
			if (DbMeetingEntity != null) {
				List<String> inviteeList = meetingDto.getMeetingEntity().getInvites().stream().distinct()
						.collect(Collectors.toList());
				String organizeremail = userRepository.getemailid(meetingDto.getMeetingEntity().getUser().getId());
				for (String invitee : inviteeList) {
					Optional<UserEntity> user = userRepository.findOneByEmailIgnoreCase(invitee);

					if (user.isPresent()) {
						UserEntity userEntity = user.get();
						emails.add(userEntity.getEmail());
						emailService.sendInvitationMailToUser(userEntity.getEmail(), DbMeetingEntity.getMeetingId(),
								userEntity.getId(), meetingDto, organizeremail);
					}
				}
			}
			map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.MEETING_SAVE_SUCCESSFULLY);
			map.put(ApplicationConstant.RESPONSE_DATA, DbMeetingEntity);
			return map;
		} else {
			map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.MEETING_NOT_SAVED);
			map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
			return map;
		}
	}

	@Override
	public UserEntity populateGuestUserData(final String invitee) {
		UserEntity user = new UserEntity();
		user.setEmail(invitee);
		user.setIsGuest(true);
		return user;
	}

	private MeetingEntity populateMeetingData(MeetingDto meetingDto) {
		MeetingEntity meetingEntity = new MeetingEntity();

		meetingEntity.setRoomName(meetingDto.getMeetingEntity().getRoomName());
		meetingEntity.setMeetingTitle(meetingDto.getMeetingEntity().getMeetingTitle());
		meetingEntity.setUser(meetingDto.getMeetingEntity().getUser());

		meetingEntity.setMeetingDesc(meetingDto.getMeetingEntity().getMeetingDesc());
		meetingEntity.setStartTime(meetingDto.getMeetingEntity().getStartDate());
		meetingEntity.setEndTime(meetingDto.getMeetingEntity().getEndDate());
		meetingEntity.setCreatedOn(LocalDateTime.now());
		meetingEntity.setModifiedOn(LocalDateTime.now());

		List<String> inviteeList = meetingDto.getMeetingEntity().getInvites().stream().distinct()
				.collect(Collectors.toList());
		for (String inv : inviteeList) {
			Optional<UserEntity> dbUser = userRepository.findOneByEmailIgnoreCase(inv);

			// adding guest user in table with email id
			if (!dbUser.isPresent()) {
				UserEntity userModel = populateGuestUserData(inv);
				userRepository.save(userModel);
			}
			Optional<UserEntity> user = userRepository.findOneByEmailIgnoreCase(inv);
			if (user.isPresent()) {
				UserEntity userEntity = user.get();

				MeetingInviteeEntity meetingInviteeEntity = new MeetingInviteeEntity();
				meetingInviteeEntity.setMeetingEntity(meetingEntity);
				meetingInviteeEntity.setInviteeId(userEntity.getId());
				meetingInviteeRepository.save(meetingInviteeEntity);
			}
		}
		return meetingEntity;
	}

	@Override
	public Map<String, Object> deleteMeetingAPI(Long meetingId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			meetingInviteeRepository.deleteFromInvitee(meetingId);
			meetingRepository.deleteFromMeeting(meetingId);
			map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.MEETING_DELETED_SUCCESSFULLY);
			map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
		} catch (Exception e) {
			map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
			map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
		}
		return map;
	}

	@Override
	public Map<String, Object> editMeetingAPI(MeetingDto meetingDto) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			if (meetingDto != null && meetingDto.getMeetingEntity() != null
					&& meetingDto.getMeetingEntity().getMeetingId() != null) {
				MeetingEntity meetingEntity = populateMeetingDataForEdit(meetingDto);
				meetingEntity.setMeetingId(meetingDto.getMeetingEntity().getMeetingId());
				meetingRepository.save(meetingEntity);
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.MEETING_EDIT_SUCCESSFULLY);
				map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
			} else {
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.MEETING_EDIT_FAILURE);
				map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
			}
			return map;
		} catch (Exception e) {
			map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.SOMETING_WENT_WRONG);
			map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
		}
		return map;
	}

	private MeetingEntity populateMeetingDataForEdit(MeetingDto meetingDto) {
		MeetingEntity meetingEntity = new MeetingEntity();
		meetingEntity.setRoomName(meetingDto.getMeetingEntity().getRoomName());
		meetingEntity.setMeetingTitle(meetingDto.getMeetingEntity().getMeetingTitle());
		meetingEntity.setUser(meetingDto.getMeetingEntity().getUser());
		meetingEntity.setMeetingDesc(meetingDto.getMeetingEntity().getMeetingDesc());
		meetingEntity.setStartTime(meetingDto.getMeetingEntity().getStartDate());
		meetingEntity.setEndTime(meetingDto.getMeetingEntity().getEndDate());
		meetingEntity.setCreatedOn(LocalDateTime.now());
		meetingEntity.setModifiedOn(LocalDateTime.now());

		if (meetingDto.getMeetingEntity().getMeetingId() != null) {
			meetingEntity.setMeetingId(meetingDto.getMeetingEntity().getMeetingId());
		}

		List<MeetingInviteeEntity> existingInviteeEntity = meetingInviteeRepository
				.findbyMeetingId(meetingDto.getMeetingEntity().getMeetingId());

		meetingInviteeRepository.deleteAll(existingInviteeEntity);

		List<String> newList = meetingDto.getMeetingEntity().getInvites().stream().distinct()
				.collect(Collectors.toList());

		for (String inv : newList) {
			Optional<UserEntity> user = userRepository.findOneByEmailIgnoreCase(inv);
			if (user.isPresent()) {
				UserEntity userEntity = user.get();
				MeetingInviteeEntity meetingInviteeEntity = new MeetingInviteeEntity();
				meetingInviteeEntity.setMeetingEntity(meetingEntity);
				meetingInviteeEntity.setInviteeId(userEntity.getId());
				meetingInviteeRepository.addMeetingInvitee(meetingEntity.getMeetingId(), userEntity.getId());
			}
		}
		return meetingEntity;
	}

	@Override
	public MeetingEntity getByMeetingId(Long meetingId) {

		MeetingEntity meetingEntity = meetingRepository.getOne(meetingId);
		return populateMeetingEntityData(meetingEntity);
	}

	@Override
	public Map<String, Object> getMeetingByDateAPI(Date meetingDate, Integer userId, String searchText) {
		Map<String, Object> map = new HashMap<String, Object>();

		List<MeetingEntity> meetingEntity = meetingRepository.findByStartTimeBetweenIgnorecaEntities(meetingDate,
				userId.longValue());

		System.out.println("meetingEntity.size() ===> " + meetingEntity.size());

		for (MeetingEntity meetingEntityobj : meetingEntity) {

			System.out.println("in else");
			List<MeetingInviteeEntity> meetingInviteeEntities = meetingInviteeRepository
					.findbyMeetingId(meetingEntityobj.getMeetingId());

			List<String> inviteeEmail = new ArrayList<>();

			for (MeetingInviteeEntity meetingInviteeEntity : meetingInviteeEntities) {
				UserEntity userData = userRepository.getOne(meetingInviteeEntity.getInviteeId());
				inviteeEmail.add(userData.getEmail());
			}
			meetingEntityobj.setInvites(inviteeEmail);
		}
		map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
		if (searchText != null) {
			List<MeetingEntity> entity = meetingEntity.stream()
					.filter(contact -> contact.getMeetingTitle().toLowerCase().contains(searchText.toLowerCase()))
					.collect(Collectors.toList());
			if (entity.size() > 0) {
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.MEETING_LIST_SUCCESS);
				map.put(ApplicationConstant.RESPONSE_DATA, entity);
			} else {
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.MEETING_LIST_NOT_FOUND);
				map.put(ApplicationConstant.RESPONSE_DATA, entity);
			}
		} else {
			if (meetingEntity.size() > 0) {
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.MEETING_LIST_SUCCESS);
				map.put(ApplicationConstant.RESPONSE_DATA, meetingEntity);
			} else {
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.MEETING_LIST_NOT_FOUND);
				map.put(ApplicationConstant.RESPONSE_DATA, meetingEntity);
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> validateRoom(String roomName) {

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapResponse = new HashMap<String, Object>();
		List<Long> mapResponse2 = new ArrayList<>();
		List<MeetingEntity> meetingEntity = new ArrayList<>();

		meetingEntity = meetingRepository.findAll(hasRoomName(roomName));
		if (meetingEntity != null && meetingEntity.size() > 0) {
			for (MeetingEntity meetingEntity2 : meetingEntity) {
				List<MeetingInviteeEntity> existingInviteeEntity = meetingInviteeRepository
						.findbyMeetingId(meetingEntity2.getMeetingId());
				mapResponse.put("meetingId", meetingEntity2.getMeetingId());
				mapResponse.put("meetingTitle", meetingEntity2.getMeetingTitle());

				System.out.println("meetingEntity2.getInvites() ===> " + meetingEntity2.getInvites());
				if (existingInviteeEntity != null && existingInviteeEntity.size() > 0) {
					for (MeetingInviteeEntity entity : existingInviteeEntity) {
						mapResponse2.add(entity.getInviteeId());
					}
				}
				mapResponse.put("invitee", mapResponse2);
			}
			map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.MEETING_ROOM_EXISTS);
			map.put(ApplicationConstant.RESPONSE_DATA, mapResponse);

		} else {
			map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.MEETING_ROOM_NOT_EXISTS);
			map.put(ApplicationConstant.RESPONSE_DATA, new ArrayList<>());
		}
		return map;
	}

	static Specification<MeetingEntity> hasRoomName(String roomName) {
		return (users, cq, cb) -> cb.or(cb.like(cb.lower(users.get("roomName")), "%" + roomName.toLowerCase() + "%"));
	}

	private MeetingEntity populateMeetingEntityData(MeetingEntity meetingEntityData) {
		MeetingEntity meetingEntity = new MeetingEntity();
		meetingEntity.setMeetingId(meetingEntityData.getMeetingId());
		meetingEntity.setRoomName(meetingEntityData.getRoomName());
		meetingEntity.setMeetingTitle(meetingEntityData.getMeetingTitle());
		meetingEntity.setUser(meetingEntityData.getUser());
		meetingEntity.setMeetingDesc(meetingEntityData.getMeetingDesc());
		meetingEntity.setCreatedOn(meetingEntityData.getCreatedOn());
		meetingEntity.setModifiedOn(meetingEntityData.getModifiedOn());
		meetingEntity.setInvites(meetingEntityData.getInvites());
		meetingEntity.setStartTime(meetingEntityData.getStartDate());
		meetingEntity.setEndTime(meetingEntityData.getEndDate());
		return meetingEntity;
	}
}
