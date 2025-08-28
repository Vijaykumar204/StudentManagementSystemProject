package com.studentmanagementsystem.api.model.custom.quarterlyreport;

public class ComplianceAndNonComplianceReportDto {
	
	
	private Long studentId;
	private String quarterAndYear;
	private String studentFirstName;
	private String studentMiddleName;
	private String studentLastName;
	private String  attendanceComplianceStatus;
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
	public String getAttendanceComplianceStatus() {
		return attendanceComplianceStatus;
	}
	public void setAttendanceComplianceStatus(String attendanceComplianceStatus) {
		this.attendanceComplianceStatus = attendanceComplianceStatus;
	}
	public String getQuarterAndYear() {
		return quarterAndYear;
	}
	public void setQuarterAndYear(String quarterAndYear) {
		this.quarterAndYear = quarterAndYear;
	}
	public ComplianceAndNonComplianceReportDto(Long studentId, String quarterAndYear, String studentFirstName,
			String studentMiddleName, String studentLastName, String attendanceComplianceStatus) {
		
		this.studentId = studentId;
		this.quarterAndYear = quarterAndYear;
		this.studentFirstName = studentFirstName;
		this.studentMiddleName = studentMiddleName;
		this.studentLastName = studentLastName;
		this.attendanceComplianceStatus = attendanceComplianceStatus;
	}
	
	
	

	
	


}
