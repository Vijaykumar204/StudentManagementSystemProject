package com.studentmanagementsystem.api.service;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.Response;

import jakarta.mail.MessagingException;

public interface EmailSentService {

	Response sendQuarterlyAttendanceReport(String quarterAndYear, Integer classOfStudy, Long teacherId)
			throws MessagingException;

	Response sendQuarterlyMarkResult(String quarterAndYear, Integer classOfStudy, Long teacherId)
			throws MessagingException;

	Response runAbsentAlertMessage(Long teacherId) throws MessagingException;

	void absentAlertEmail(List<Long> absentIdList, Long teacherId) throws MessagingException;

}
