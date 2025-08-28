package com.studentmanagementsystem.api.model.custom.dailyattendance;

import java.time.LocalDate;



public class DailyAttendanceDto {
	
	
	private Long attendanceId;
	
	private LocalDate attendanceDate;
	
	private char attendanceStatus;
	
	private char longApprovedSickLeaveFlag='N';
	
	private char approvedExtraCurricularActivitiesFlag = 'N';
	
	private Long studentId;
	
	private String studentFirstName;
	private String studentMiddleName;
	private String studentLastName;

	public Long getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(Long attendanceId) {
		this.attendanceId = attendanceId;
	}

	public LocalDate getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(LocalDate attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public char getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(char attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}

	public char getLongApprovedSickLeaveFlag() {
		return longApprovedSickLeaveFlag;
	}

	public void setLongApprovedSickLeaveFlag(char longApprovedSickLeaveFlag) {
		this.longApprovedSickLeaveFlag = longApprovedSickLeaveFlag;
	}

	public char getApprovedExtraCurricularActivitiesFlag() {
		return approvedExtraCurricularActivitiesFlag;
	}

	public void setApprovedExtraCurricularActivitiesFlag(char approvedExtraCurricularActivitiesFlag) {
		this.approvedExtraCurricularActivitiesFlag = approvedExtraCurricularActivitiesFlag;
	}

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

//	public DailyAttendanceDto(Long attendanceId, LocalDate attendanceDate, char attendanceStatus,
//			char longApprovedSickLeaveFlag, char approvedExtraCurricularActivitiesFlag, Long studentId) {
//		
//		this.attendanceId = attendanceId;
//		this.attendanceDate = attendanceDate;
//		this.attendanceStatus = attendanceStatus;
//		this.longApprovedSickLeaveFlag = longApprovedSickLeaveFlag;
//		this.approvedExtraCurricularActivitiesFlag = approvedExtraCurricularActivitiesFlag;
//		this.studentId = studentId;
//	}

	public DailyAttendanceDto() {
		
	}



	public DailyAttendanceDto(LocalDate attendanceDate, char attendanceStatus, Long studentId, String studentFirstName,
			String studentMiddleName, String studentLastName) {
		
	
		this.attendanceDate = attendanceDate;
		this.attendanceStatus = attendanceStatus;
		this.studentId = studentId;
		this.studentFirstName = studentFirstName;
		this.studentMiddleName = studentMiddleName;
		this.studentLastName = studentLastName;
		
		
	}

	public DailyAttendanceDto(Long studentId, String studentFirstName, String studentMiddleName,
			String studentLastName) {
		super();
		this.studentId = studentId;
		this.studentFirstName = studentFirstName;
		this.studentMiddleName = studentMiddleName;
		this.studentLastName = studentLastName;
	}

	public DailyAttendanceDto(LocalDate attendanceDate, Long studentId, String studentFirstName,
			String studentMiddleName, String studentLastName) {
		super();
		this.attendanceDate = attendanceDate;
		this.studentId = studentId;
		this.studentFirstName = studentFirstName;
		this.studentMiddleName = studentMiddleName;
		this.studentLastName = studentLastName;
	}

	public DailyAttendanceDto(LocalDate attendanceDate) {
		
		this.attendanceDate = attendanceDate;
	}
	
	
	
	

	

	
	

}
