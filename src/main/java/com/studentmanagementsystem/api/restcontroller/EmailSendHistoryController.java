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

	
	@GetMapping("/quarterresult")
	ResponseEntity<?> sendQuarterlyResultReport(@RequestParam String quarterAndYear) throws MessagingException{
		return new ResponseEntity<>(emailSentService.sendQuarterlyResultReport(quarterAndYear),HttpStatus.OK);
	}
	@GetMapping("/absentAlert")
	ResponseEntity<?> runAbsentAlertMessage() throws MessagingException{
		return new ResponseEntity<>(emailSentService.runAbsentAlertMessage(),HttpStatus.OK);
	}
	
}
