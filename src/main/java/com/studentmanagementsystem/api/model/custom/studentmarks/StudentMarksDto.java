package com.studentmanagementsystem.api.model.custom.studentmarks;


public class StudentMarksDto {
	
	

	private Long StudentId;
	
	private String quarterAndYear;
	
	
	private int tamil;
	
	
	private int english;
	
	
	private int maths;
	
	
	private int science;
	
	
	private int socialScience;
	
	
	private int totalMarks;
	
	
	private char result;
	
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


	public int getTamil() {
		return tamil;
	}


	public void setTamil(int tamil) {
		this.tamil = tamil;
	}


	public int getEnglish() {
		return english;
	}


	public void setEnglish(int english) {
		this.english = english;
	}


	public int getMaths() {
		return maths;
	}


	public void setMaths(int maths) {
		this.maths = maths;
	}


	public int getScience() {
		return science;
	}


	public void setScience(int science) {
		this.science = science;
	}


	public int getSocialScience() {
		return socialScience;
	}


	public void setSocialScience(int socialScience) {
		this.socialScience = socialScience;
	}


	public int getTotalMarks() {
		return totalMarks;
	}


	public void setTotalMarks(int totalMarks) {
		this.totalMarks = totalMarks;
	}


	public char getResult() {
		return result;
	}


	public void setResult(char result) {
		this.result = result;
	}

	


	public Long getTeacherId() {
		return teacherId;
	}


	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}




	public StudentMarksDto(Long studentId, String quarterAndYear, int tamil, int english, int maths, int science,
			int socialScience, int totalMarks, char result) {
	
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
