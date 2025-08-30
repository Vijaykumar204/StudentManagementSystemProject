package com.studentmanagementsystem.api.model.custom.studentmarks;

public class ClassTopperDto {
	
	private Long studentId;
	
	private String quarterAndYear;
	
	private String StudentFirstName;
	
	private String StudentMiddleName;
	
	private String StudentLastName;
	
	private int totalMark;

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

	public String getStudentFirstName() {
		return StudentFirstName;
	}

	public void setStudentFirstName(String studentFirstName) {
		StudentFirstName = studentFirstName;
	}

	public String getStudentMiddleName() {
		return StudentMiddleName;
	}

	public void setStudentMiddleName(String studentMiddleName) {
		StudentMiddleName = studentMiddleName;
	}

	public String getStudentLastName() {
		return StudentLastName;
	}

	public void setStudentLastName(String studentLastName) {
		StudentLastName = studentLastName;
	}

	public int getTotalMark() {
		return totalMark;
	}

	public void setTotalMark(int totalMark) {
		this.totalMark = totalMark;
	}

	public ClassTopperDto(Long studentId, String quarterAndYear, String studentFirstName, String studentMiddleName,
			String studentLastName, int totalMark) {
		
		this.studentId = studentId;
		this.quarterAndYear = quarterAndYear;
		StudentFirstName = studentFirstName;
		StudentMiddleName = studentMiddleName;
		StudentLastName = studentLastName;
		this.totalMark = totalMark;
	}
	
	
	
	

}
