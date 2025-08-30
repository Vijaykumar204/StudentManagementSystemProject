package com.studentmanagementsystem.api.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "email_sent_history")
public class EmailSentHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "Email_Id")
    private Long emailId;
	
	@Column(name = "Student_Id",nullable = false)
    private Long studentId;
	
	@Column(name = "Student_Email" ,nullable = false,length = 30)
    private String studentEmail;
	
	@Column(name = "Email_Subject",nullable = false,length = 100)
    private String emailSubject;
	
	@Column(name = "Email_Message",nullable = false)
    private String emailMessage;
	
	@Column(name = "Email_Sent_Date",nullable = false)
    private LocalDateTime mailSentDate;

	public Long getEmailId() {
		return emailId;
	}

	public void setEmailId(Long emailId) {
		this.emailId = emailId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getStudentEmail() {
		return studentEmail;
	}

	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailMessage() {
		return emailMessage;
	}

	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
	}

	public LocalDateTime getMailSentDate() {
		return mailSentDate;
	}

	public void setMailSentDate(LocalDateTime mailSentDate) {
		this.mailSentDate = mailSentDate;
	}
	
	
	

}
