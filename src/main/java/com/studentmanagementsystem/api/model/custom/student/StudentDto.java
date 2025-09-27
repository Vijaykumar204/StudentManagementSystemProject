package com.studentmanagementsystem.api.model.custom.student;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.studentmanagementsystem.api.util.WebServiceUtil;


public class StudentDto {

    private Integer sno;
    
	private Long studentId;
	
	private String name;
	
	private String gender;
	
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern =WebServiceUtil.APP_DATE_FORMAT)
	private LocalDate dateOfBirth;

	private Integer classOfStudy;

	private String residingStatus;
	
	private String email;

	private String phoneNumber;

	private String  parentsName;
	
	private String parentsEmail;

	private String homeStreetName;

	private String homeCityName;

	private String homePostalCode;

	private String status;
	
	private Long teacherId;
	
	private String firstName;
	
	private String middleName;
	
	private String lastName;

	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Integer getClassOfStudy() {
		return classOfStudy;
	}
	public void setClassOfStudy(Integer classOfStudy) {
		this.classOfStudy = classOfStudy;
	}
	public String getResidingStatus() {
		return residingStatus;
	}
	public void setResidingStatus(String residingStatus) {
		this.residingStatus = residingStatus;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getParentsName() {
		return parentsName;
	}
	public void setParentsName(String parentsName) {
		this.parentsName = parentsName;
	}
	public String getParentsEmail() {
		return parentsEmail;
	}
	public void setParentsEmail(String parentsEmail) {
		this.parentsEmail = parentsEmail;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	public Integer getSno() {
		return sno;
	}
	public void setSno(Integer sno) {
		this.sno = sno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public StudentDto(Long studentId, String name, String gender,
			LocalDate dateOfBirth, Integer classOfStudy, String residingStatus, String phoneNumber, String parentsName,
			 String homeStreetName, String homeCityName, String homePostalCode, String status,
			String email,String parentsEmail,String firstName,String middleName,String lastName ) {

		this.studentId = studentId;
		this.name = name;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.classOfStudy = classOfStudy;
		this.residingStatus = residingStatus;
		this.phoneNumber = phoneNumber;
		this.parentsName = parentsName;		
		this.homeStreetName = homeStreetName;
		this.homeCityName = homeCityName;
		this.homePostalCode = homePostalCode;
		this.status = status;
		this.email = email;
		this.parentsEmail = parentsEmail;
		this.firstName=firstName;
		this.middleName=middleName;
		this.lastName=lastName;
	}
	public StudentDto() {
	    }

		


    
    
    
    
    
	
}
