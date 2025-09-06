package com.studentmanagementsystem.api.model.custom.studentmarks;

public class StudentWithPassOrFail {
	
    private Long StudentId;
    
	private String quarterAndYear;
	
	private String result;
	
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
	
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}


	
	public StudentWithPassOrFail(Long studentId, String quarterAndYear, String result) {
		
		StudentId = studentId;
		this.quarterAndYear = quarterAndYear;
		this.result = result;
	}
	public StudentWithPassOrFail() {
		
	}
	
	
}
