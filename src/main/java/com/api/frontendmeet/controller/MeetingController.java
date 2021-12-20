package com.api.frontendmeet.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.api.frontendmeet.Entity.MeetingDto;
import com.api.frontendmeet.Entity.MeetingEntity;
import com.api.frontendmeet.repository.MeetingRepository;
import com.api.frontendmeet.service.MeetingService;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

	public static final Logger logger = LoggerFactory.getLogger(MeetingController.class);

	@Autowired
	MeetingService meetingService;

	@Autowired
	MeetingRepository meetingRepository;

	@PostMapping("/saveMeetingAPI")
	public ResponseEntity<Map<String, Object>> saveMeetingAPI(@RequestBody MeetingDto meetingDto) {
		try {
			logger.info("Inside saveMeetingAPI : " + meetingDto.getMeetingEntity().getUser());
			return new ResponseEntity<>(meetingService.saveMeetingAPI(meetingDto), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error occured while save meeting {} :Reason :{}",
					// meetingDto.getMeetingTitle(),
					e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/deleteMeetingAPI")
	public ResponseEntity<Map<String, Object>> deleteMeetingAPI(@RequestParam(value = "meetingId") Integer meetingId) {
		try {
			logger.info("Inside deleteMeetingAPI userId : " + meetingId);
			return new ResponseEntity<>(meetingService.deleteMeetingAPI(meetingId.longValue()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error occured while deleteMeetingAPI userId {} :Reason :{}", meetingId, e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/editMeetingAPI")
	public ResponseEntity<Map<String, Object>> editMeetingAPI(@RequestBody MeetingDto meetingDto) {
		try {
			logger.info("editMeetingAPI : " + meetingDto.getMeetingEntity().getUser());
			return new ResponseEntity<>(meetingService.editMeetingAPI(meetingDto), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error occured while editMeetingAPI meeting {} :Reason :{}",
					meetingDto.getMeetingEntity().getMeetingTitle(), e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/getMeetingById")
	public ResponseEntity<MeetingEntity> getMeetingById(@RequestParam(value = "meetingId") Long meetingId) {
		try {
			logger.info("Inside getMeetingById : " + meetingId);
			return new ResponseEntity<>(meetingService.getByMeetingId(meetingId), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error occured while getMeetingById {} :Reason :{}", meetingId, e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/getMeetingListByDateAPI")
	public ResponseEntity<Map<String, Object>> getMeetingListByDateAPI(@RequestBody String userData) {
		Date meetingDate = null;
		try {
			JSONObject jsonObject = new JSONObject(userData);
			String search = null;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String meetingDateData = jsonObject.getString("meetingDate");
			meetingDate = dateFormat.parse(meetingDateData);
			Integer userId = jsonObject.getInt("userId");

			if (!jsonObject.isNull("search")) {
				search = jsonObject.getString("search");
			}
			logger.info("Inside getMeetingListByDateAPI : " + userData);
			return new ResponseEntity<>(meetingService.getMeetingByDateAPI(meetingDate, userId, search), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error occured while getMeetingListByDateAPI {} :Reason :{}", meetingDate, e.getMessage());
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/validateRoom")
	public ResponseEntity<Map<String, Object>> validateRoom(@RequestParam(value = "roomName") String roomName) {
		try {
			logger.info("Inside validateRoom userId : " + roomName);
			return new ResponseEntity<>(meetingService.validateRoom(roomName), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error occured while validateRoom roomaName {} :Reason :{}", roomName, e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
