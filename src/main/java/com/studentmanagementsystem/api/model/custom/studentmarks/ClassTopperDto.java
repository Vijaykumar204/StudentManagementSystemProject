package com.studentmanagementsystem.api.model.custom.studentmarks;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.studentmanagementsystem.api.util.WebServiceUtil;

public class ClassTopperDto {
	
	private Long studentId;
	
	private String name;
	    
	private Integer classOfStudy;
	    
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern =WebServiceUtil.APP_DATE_FORMAT)
	private LocalDate dateOfBirth;
		
	private String phoneNumber;
		
	private String email;
	
	private String quarterAndYear;
	
	private Integer totalMark;
	
	private Integer percentage;

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getQuarterAndYear() {
		return quarterAndYear;
	}

	public void setQuarterAndYear(String quarterAndYear) {
		this.quarterAndYear = quarterAndYear;
	}


	public Integer getTotalMark() {
		return totalMark;
	}

	public void setTotalMark(Integer totalMark) {
		this.totalMark = totalMark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getClassOfStudy() {
		return classOfStudy;
	}

	public void setClassOfStudy(Integer classOfStudy) {
		this.classOfStudy = classOfStudy;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}

//	public ClassTopperDto(Long studentId, String quarterAndYear, String studentFirstName, String studentMiddleName,
//			String studentLastName, Integer totalMark) {
//		
//		this.studentId = studentId;
//		this.quarterAndYear = quarterAndYear;
//		StudentFirstName = studentFirstName;
//		StudentMiddleName = studentMiddleName;
//		StudentLastName = studentLastName;
//		this.totalMark = totalMark;
//	}
	
	
	
	

}
