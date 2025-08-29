package com.studentmanagementsystem.api.model.custom.student;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.studentmanagementsystem.api.util.WebServiceUtil;


public class StudentListRequestDto {

	private Long studentId;
	
	private String studentFirstName;
	
	private String studentMiddleName;
	
	private String studentLastName;
	
	private char studentGender;
	
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern =WebServiceUtil.APP_DATE_FORMAT)
	private LocalDate studentDateOfBirth;

	private int studentClassOfStudy;

	private char studentResidingStatus;

	private String studentPhoneNumber;

	private String  emergencyContactPersonName;

	private String emergencyContactPhoneNumber;

	private String homeStreetName;

	private String homeCityName;

	private String homePostalCode;

	private char studentActiveStatus;
	
	private String studentEmail;

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

	public char getStudentGender() {
		return studentGender;
	}

	public void setStudentGender(char studentGender) {
		this.studentGender = studentGender;
	}

	public LocalDate getStudentDateOfBirth() {
		return studentDateOfBirth;
	}

	public void setStudentDateOfBirth(LocalDate studentDateOfBirth) {
		this.studentDateOfBirth = studentDateOfBirth;
	}

	public int getStudentClassOfStudy() {
		return studentClassOfStudy;
	}

	public void setStudentClassOfStudy(int studentClassOfStudy) {
		this.studentClassOfStudy = studentClassOfStudy;
	}

	public char getStudentResidingStatus() {
		return studentResidingStatus;
	}

	public void setStudentResidingStatus(char studentResidingStatus) {
		this.studentResidingStatus = studentResidingStatus;
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

	public char getStudentActiveStatus() {
		return studentActiveStatus;
	}

	public void setStudentActiveStatus(char studentActiveStatus) {
		this.studentActiveStatus = studentActiveStatus;
	}

	public String getStudentEmail() {
		return studentEmail;
	}

	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}

	public StudentListRequestDto(Long studentId, String studentFirstName, String studentMiddleName,
			String studentLastName, char studentGender, LocalDate studentDateOfBirth, int studentClassOfStudy,
			char studentResidingStatus, String studentPhoneNumber, String emergencyContactPersonName,
			String emergencyContactPhoneNumber, String homeStreetName, String homeCityName, String homePostalCode,
			char studentActiveStatus, String studentEmail) {
		super();
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
	}

		


    
    
    
    
    
	
}
