package com.studentmanagementsystem.api.model.custom.studentmarks;

public class ComplianceStudentWithPassOrFail {
    private Long StudentId;
	private String quarterAndYear;
	private char result;
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
	public char getResult() {
		return result;
	}
	public void setResult(char result) {
		this.result = result;
	}
	public ComplianceStudentWithPassOrFail(Long studentId, String quarterAndYear, char result) {
		
		StudentId = studentId;
		this.quarterAndYear = quarterAndYear;
		this.result = result;
	}
	
}
