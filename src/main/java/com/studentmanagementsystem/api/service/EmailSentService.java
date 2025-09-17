package com.studentmanagementsystem.api.service;

import com.studentmanagementsystem.api.model.custom.Response;

import jakarta.mail.MessagingException;

public interface EmailSentService {

	Response sendQuarterlyAttendanceReport(String quarterAndYear, Integer classOfStudy,Long teacherId) throws MessagingException;

	Response sendQuarterlyMarkResult(String quarterAndYear, Integer classOfStudy,Long teacherId) throws MessagingException;

	Response runAbsentAlertMessage(Long teacherId) throws MessagingException;

}
