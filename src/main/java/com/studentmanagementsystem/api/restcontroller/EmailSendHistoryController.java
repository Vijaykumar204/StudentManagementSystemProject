package com.studentmanagementsystem.api.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studentmanagementsystem.api.serviceimpl.EmailSentService;

import jakarta.mail.MessagingException;

@RestController
public class EmailSendHistoryController {
	
	@Autowired
	private EmailSentService emailSentService;

	
	/**
	 * Send the quarterly attendance report and result report to student parents via email.
	 *
	 * @param quarterAndYear The quarter and year for which the reports are to be sent (e.g., Q1-2025)
	 * @return Confirmation message indicating that the reports were sent successfully
	 * @author Vijiyakumar
	 */
	@GetMapping("/quarterresult")
	ResponseEntity<?> sendQuarterlyResultReport(@RequestParam String quarterAndYear) throws MessagingException{
		return new ResponseEntity<>(emailSentService.sendQuarterlyResultReport(quarterAndYear),HttpStatus.OK);
	}
	
	/**
	 * Send the absent alert to student parents via email.
	 *
	 * @return Confirmation message indicating that the alert were sent successfully
	 * @author Vijiyakumar
	 */
	@GetMapping("/absentAlert")
	ResponseEntity<?> runAbsentAlertMessage() throws MessagingException{
		return new ResponseEntity<>(emailSentService.runAbsentAlertMessage(),HttpStatus.OK);
	}
	
}
