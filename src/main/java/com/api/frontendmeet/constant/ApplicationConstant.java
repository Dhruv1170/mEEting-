package com.api.frontendmeet.constant;

public class ApplicationConstant {

	// Common
	public static final String SOMETING_WENT_WRONG = "SOMETING_WENT_WRONG!";

	// login
	public static final String LOGIN_SUCCESS = "LOGIN_SUCCESS";
	public static final String LOGIN_INVALID_CREDENTIALS = "LOGIN_INVALID_CREDENTIALS";
	public static final String LOGIN_NOT_VERIFIED = "LOGIN_NOT_VERIFIED";

	// Registration
	public static final String REGISTRATION_SUCCESS = "REGISTRATION_SUCCESS!";
	public static final String REGISTRATION_EMAIL_EXISTS = "REGISTRATION_EMAIL_EXISTS.";

	// Forgot Password
	public static final String FORGOT_PASSWORD_SUCCESS = "FORGOT_PASSWORD_SUCCESS!";
	public static final String FORGOT_PASSWORD_EMAIL_NOT_EXISTS = "FORGOT_PASSWORD_EMAIL_NOT_EXISTS!";

	// Reset Password
	public static final String RESET_PASSWORD_SUCCESS = "RESET_PASSWORD_SUCCESS!";
	public static final String RESET_PASSWORD_WRONG_PASSWORD = "RESET_PASSWORD_WRONG_PASSWORD.";

	// Response Status Code
	public static final String STATUS_200 = "200";
	public static final String STATUS_400 = "400";

	// Response Format
	public static final String RESPONSE_STATUS = "Status";
	public static final String RESPONSE_MESSAGE = "message";
	public static final String RESPONSE_DATA = "data";

	// Meeting
	public static final String MEETING_LIST_SUCCESS = "MEETING_LIST_SUCCESS !";
	public static final String MEETING_LIST_NOT_FOUND = "MEETING_LIST_NOT_FOUND !";
	public static final String MEETING_SAVE_SUCCESSFULLY = "MEETING_SAVE_SUCCESSFULLY!";
	public static final String MEETING_NOT_SAVED = "MEETING_NOT_SAVED!";
	public static final String MEETING_DELETED_SUCCESSFULLY = "MEETING_DELETED_SUCCESSFULLY!";
	public static final String MEETING_ROOM_EXISTS = "MEETING_ROOM_EXISTS!";
	public static final String MEETING_ROOM_NOT_EXISTS = "MEETING_ROOM_NOT_EXISTS!";
	public static final String MEETING_EDIT_SUCCESSFULLY = "MEETING_EDIT_SUCCESSFULLY !";
	public static final String MEETING_EDIT_FAILURE = "MEETING_EDIT_FAILURE!";

	// Document
	public static final String DOCUMENT_LIST_SUCCESS = "DOCUMENT_LIST_SUCCESS!";
	public static final String DOCUMENT_LIST_NOT_FOUND = "DOCUMENT_LIST_NOT_FOUND!";
	public static final String DOCUMENT_DELETE_SUCCESS = "DOCUMENT_DELETE_SUCCESS!";
	public static final String DOCUMENT_NOT_FOUND = "DOCUMENT_NOT_FOUND!";
	public static final String DOCUMENT_SOURCE_PROFILE = "Profile";
	public static final String DOCUMENT_SOURCE_DOCUMENTS = "Documents";
	public static final String DOCUMENT_SOURCE_RECORDINGS = "Recordings";
	public static final String DOCUMENT_UPLOAD_STATUS_UPLOADING = "UPLOADING";
	public static final String DOCUMENT_UPLOAD_STATUS_COMPLETED = "COMPLETED";

	// User
	public static final String USER_SUCCESS = "USER_SUCCESS!";
	public static final String USER_NOT_FOUND = "USER_NOT_FOUND!";
	public static final String USER_VERIFICATION_SUCCESS = "USER_VERIFICATION_SUCCESS!";
	public static final String USER_VERIFICATION_FAILURE = "USER_VERIFICATION_FAILURE!";
	public static final String USER_EDIT_SUCCESS = "USER_EDIT_SUCCESS!";

	public static final String DOCUMENT_SOURCE_TERMS = "TermsAndConditions";
	public static final String TERMS_AND_CONDITIONS = "TermsAndCondition.pdf";

	public static final String EMAILPRODUCTID = "-//Microsoft Corporation//Outlook 11.0 MIMEDIR//EN";
}
