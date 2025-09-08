package com.studentmanagementsystem.api.model.custom.student;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.studentmanagementsystem.api.util.WebServiceUtil;


public class StudentDto {

	private Long studentId;
	
	private String studentFirstName;
	
	private String studentMiddleName;
	
	private String studentLastName;
	
	private String studentGender;
	
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern =WebServiceUtil.APP_DATE_FORMAT)
	private LocalDate studentDateOfBirth;

	private Integer studentClassOfStudy;

	private String studentResidingStatus;

	private String studentPhoneNumber;

	private String  emergencyContactPersonName;

	private String emergencyContactPhoneNumber;
	
	private String contactEmail;

	private String homeStreetName;

	private String homeCityName;

	private String homePostalCode;

	private String studentActiveStatus;
	
	private String studentEmail;
	
	private Long teacherId;

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getStudentFirstName() {
		return studentFirstName;
	}

	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}

	public String getStudentMiddleName() {
		return studentMiddleName;
	}

	public void setStudentMiddleName(String studentMiddleName) {
		this.studentMiddleName = studentMiddleName;
	}

	public String getStudentLastName() {
		return studentLastName;
	}

	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}



	public LocalDate getStudentDateOfBirth() {
		return studentDateOfBirth;
	}

	public void setStudentDateOfBirth(LocalDate studentDateOfBirth) {
		this.studentDateOfBirth = studentDateOfBirth;
	}

	public Integer getStudentClassOfStudy() {
		return studentClassOfStudy;
	}

	public void setStudentClassOfStudy(Integer studentClassOfStudy) {
		this.studentClassOfStudy = studentClassOfStudy;
	}



	public String getStudentPhoneNumber() {
		return studentPhoneNumber;
	}

	public void setStudentPhoneNumber(String studentPhoneNumber) {
		this.studentPhoneNumber = studentPhoneNumber;
	}

	public String getEmergencyContactPersonName() {
		return emergencyContactPersonName;
	}

	public void setEmergencyContactPersonName(String emergencyContactPersonName) {
		this.emergencyContactPersonName = emergencyContactPersonName;
	}

	public String getEmergencyContactPhoneNumber() {
		return emergencyContactPhoneNumber;
	}

	public void setEmergencyContactPhoneNumber(String emergencyContactPhoneNumber) {
		this.emergencyContactPhoneNumber = emergencyContactPhoneNumber;
	}

	public String getHomeStreetName() {
		return homeStreetName;
	}

	public void setHomeStreetName(String homeStreetName) {
		this.homeStreetName = homeStreetName;
	}

	public String getHomeCityName() {
		return homeCityName;
	}

	public void setHomeCityName(String homeCityName) {
		this.homeCityName = homeCityName;
	}

	public String getHomePostalCode() {
		return homePostalCode;
	}

	public void setHomePostalCode(String homePostalCode) {
		this.homePostalCode = homePostalCode;
	}



	public String getStudentEmail() {
		return studentEmail;
	}

	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	

	public String getStudentGender() {
		return studentGender;
	}

	public void setStudentGender(String studentGender) {
		this.studentGender = studentGender;
	}

	public String getStudentResidingStatus() {
		return studentResidingStatus;
	}

	public void setStudentResidingStatus(String studentResidingStatus) {
		this.studentResidingStatus = studentResidingStatus;
	}

	public String getStudentActiveStatus() {
		return studentActiveStatus;
	}

	public void setStudentActiveStatus(String studentActiveStatus) {
		this.studentActiveStatus = studentActiveStatus;
	}
	



	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public StudentDto(Long studentId, String studentFirstName, String studentMiddleName,
			String studentLastName, String studentGender, LocalDate studentDateOfBirth, Integer studentClassOfStudy,
			String studentResidingStatus, String studentPhoneNumber, String emergencyContactPersonName,
			String emergencyContactPhoneNumber, String homeStreetName, String homeCityName, String homePostalCode,
			String studentActiveStatus, String studentEmail,String contactEmail) {
		
		this.studentId = studentId;
		this.studentFirstName = studentFirstName;
		this.studentMiddleName = studentMiddleName;
		this.studentLastName = studentLastName;
		this.studentGender = studentGender;
		this.studentDateOfBirth = studentDateOfBirth;
		this.studentClassOfStudy = studentClassOfStudy;
		this.studentResidingStatus = studentResidingStatus;
		this.studentPhoneNumber = studentPhoneNumber;
		this.emergencyContactPersonName = emergencyContactPersonName;
		this.emergencyContactPhoneNumber = emergencyContactPhoneNumber;
		this.homeStreetName = homeStreetName;
		this.homeCityName = homeCityName;
		this.homePostalCode = homePostalCode;
		this.studentActiveStatus = studentActiveStatus;
		this.studentEmail = studentEmail;
		this.contactEmail = contactEmail;
		
	}
	  public StudentDto() {
	    }

		


    
    
    
    
    
	
}
