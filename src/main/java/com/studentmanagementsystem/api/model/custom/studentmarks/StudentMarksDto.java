package com.studentmanagementsystem.api.model.custom.studentmarks;


public class StudentMarksDto {
	
	

	private Long StudentId;
	
	private String quarterAndYear;
	
	
	private Integer tamil;
	
	
	private Integer english;
	
	
	private Integer maths;
	
	
	private Integer science;
	
	
	private Integer socialScience;
	
	
	private Integer totalMarks;
	
	
	private Character result;
	
	private Long teacherId;


	public Long getStudentId() {
		return StudentId;
	}


	public void setStudentId(Long studentId) {
		StudentId = studentId;
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


	public Character getResult() {
		return result;
	}


	public void setResult(Character result) {
		this.result = result;
	}


	public Long getTeacherId() {
		return teacherId;
	}


	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}


	public StudentMarksDto(Long studentId, String quarterAndYear, Integer tamil, Integer english, Integer maths, int science,
			Integer socialScience, Integer totalMarks, Character result) {
	
		StudentId = studentId;
		this.quarterAndYear = quarterAndYear;
		this.tamil = tamil;
		this.english = english;
		this.maths = maths;
		this.science = science;
		this.socialScience = socialScience;
		this.totalMarks = totalMarks;
		this.result = result;
		
	}


	public StudentMarksDto() {
		
	}
	
	
	
	
	
	

}
