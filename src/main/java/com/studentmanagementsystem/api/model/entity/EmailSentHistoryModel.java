package com.studentmanagementsystem.api.model.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "email_sent_history")
public class EmailSentHistoryModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Email_Id")
    private Long emailId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Student_Id",referencedColumnName = "STU_Id",nullable = false)
	private StudentModel studentId;
	
	@Column(name = "Parents_Email" ,nullable = false,length = 30)
    private String studentEmail;
	
	@Column(name = "Email_Subject",nullable = false,length = 100)
    private String emailSubject;
	
	@Column(name = "Email_Message",nullable = false)
    private String emailMessage;
	
	@Column(name = "Email_Sent_Date",nullable = false)
    private LocalDateTime mailSentDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="Email_send_By" ,referencedColumnName = "TEACH_Id",nullable = false)
	 private TeacherModel teacherId;
	
	
	public Long getEmailId() {
		return emailId;
	}

	public void setEmailId(Long emailId) {
		this.emailId = emailId;
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

	public StudentModel getStudentId() {
		return studentId;
	}

	public void setStudentId(StudentModel studentId) {
		this.studentId = studentId;
	}

	public TeacherModel getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(TeacherModel teacherId) {
		this.teacherId = teacherId;
	}

}
