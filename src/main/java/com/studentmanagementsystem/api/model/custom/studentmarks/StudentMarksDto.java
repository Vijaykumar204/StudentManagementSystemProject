package com.studentmanagementsystem.api.model.custom.studentmarks;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.studentmanagementsystem.api.util.WebServiceUtil;

public class StudentMarksDto {
	

	private Integer sno;
	
	private Long studentId;
	
	private String name;
	    
	private Integer classOfStudy;
	    
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern =WebServiceUtil.APP_DATE_FORMAT)
	private LocalDate dateOfBirth;
		
	private String phoneNumber;
		
	private String email;
	
	private String quarterAndYear;
	
	private Integer tamil;
	
	
	private Integer english;
	
	
	private Integer maths;
	
	
	private Integer science;
	
	
	private Integer socialScience;
	
	
	private Integer totalMarks;
	
	private Integer percentage;
	
	private String result;
	
	private Long teacherId;





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

	public Integer getTamil() {
		return tamil;
	}


	public void setTamil(Integer tamil) {
		this.tamil = tamil;
	}


	public Integer getEnglish() {
		return english;
	}


	public void setEnglish(Integer english) {
		this.english = english;
	}


	public Integer getMaths() {
		return maths;
	}


	public void setMaths(Integer maths) {
		this.maths = maths;
	}


	public Integer getScience() {
		return science;
	}


	public void setScience(Integer science) {
		this.science = science;
	}


	public Integer getSocialScience() {
		return socialScience;
	}


	public void setSocialScience(Integer socialScience) {
		this.socialScience = socialScience;
	}


	public Integer getTotalMarks() {
		return totalMarks;
	}


	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public Long getTeacherId() {
		return teacherId;
	}


	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
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
	
	


//	public StudentMarksDto(Long studentId, String quarterAndYear, Integer tamil, Integer english, Integer maths, int science,
//			Integer socialScience, Integer totalMarks, String result) {
//	
//		StudentId = studentId;
//		this.quarterAndYear = quarterAndYear;
//		this.tamil = tamil;
//		this.english = english;
//		this.maths = maths;
//		this.science = science;
//		this.socialScience = socialScience;
//		this.totalMarks = totalMarks;
//		this.result = result;
//		
//	}

	

	public Integer getSno() {
		return sno;
	}


	public void setSno(Integer sno) {
		this.sno = sno;
	}


	public StudentMarksDto(Long studentId, String name, Integer classOfStudy, LocalDate dateOfBirth, String phoneNumber,
			String email, String quarterAndYear, Integer tamil, Integer english, Integer maths, Integer science,
			Integer socialScience, Integer totalMarks, Integer percentage, String result) {
//		super();
		this.studentId = studentId;
		this.name = name;
		this.classOfStudy = classOfStudy;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.quarterAndYear = quarterAndYear;
		this.tamil = tamil;
		this.english = english;
		this.maths = maths;
		this.science = science;
		this.socialScience = socialScience;
		this.totalMarks = totalMarks;
		this.percentage = percentage;
		this.result = result;
	}


	public StudentMarksDto() {
		
	}
	
	
	
	
	
	

}
