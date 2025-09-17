package com.studentmanagementsystem.api.model.custom.quarterlyreport;



public class QuarterlyAttendanceReportDto {

	

	private Long quarterlyAttendanceId;
	
	
	private Long totalSchoolWorkingDays;
	

	private Long totalDaysOfPresent;
	

	private Long totalDaysOfAbsents;
	

	private Long totalApprovedActivitiesPermissionDays;
	

	private Long totalApprovedSickdays;
	

	private String  attendanceComplianceStatus;
	
	private Long studentId;
	
	private String phoneNumber;
	
	private String email;

	public Long getQuarterlyAttendanceId() {
		return quarterlyAttendanceId;
	}

	public void setQuarterlyAttendanceId(Long quarterlyAttendanceId) {
		this.quarterlyAttendanceId = quarterlyAttendanceId;
	}

	public Long getTotalSchoolWorkingDays() {
		return totalSchoolWorkingDays;
	}

	public void setTotalSchoolWorkingDays(Long totalSchoolWorkingDays) {
		this.totalSchoolWorkingDays = totalSchoolWorkingDays;
	}

	public Long getTotalDaysOfPresent() {
		return totalDaysOfPresent;
	}

	public void setTotalDaysOfPresent(Long totalDaysOfPresent) {
		this.totalDaysOfPresent = totalDaysOfPresent;
	}

	public Long getTotalDaysOfAbsents() {
		return totalDaysOfAbsents;
	}

	public void setTotalDaysOfAbsents(Long totalDaysOfAbsents) {
		this.totalDaysOfAbsents = totalDaysOfAbsents;
	}

	public Long getTotalApprovedActivitiesPermissionDays() {
		return totalApprovedActivitiesPermissionDays;
	}

	public void setTotalApprovedActivitiesPermissionDays(Long totalApprovedActivitiesPermissionDays) {
		this.totalApprovedActivitiesPermissionDays = totalApprovedActivitiesPermissionDays;
	}

	public Long getTotalApprovedSickdays() {
		return totalApprovedSickdays;
	}

	public void setTotalApprovedSickdays(Long totalApprovedSickdays) {
		this.totalApprovedSickdays = totalApprovedSickdays;
	}

	public String getAttendanceComplianceStatus() {
		return attendanceComplianceStatus;
	}

	public void setAttendanceComplianceStatus(String attendanceComplianceStatus) {
		this.attendanceComplianceStatus = attendanceComplianceStatus;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
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

	public QuarterlyAttendanceReportDto(Long studentId,Long totalSchoolWorkingDays, Long totalDaysOfPresent, Long totalDaysOfAbsents, Long totalApprovedSickdays,
			Long totalApprovedActivitiesPermissionDays ) {
		
		this.studentId = studentId;
		this.totalSchoolWorkingDays = totalSchoolWorkingDays;
		this.totalDaysOfPresent = totalDaysOfPresent;
		this.totalDaysOfAbsents = totalDaysOfAbsents;
		this.totalApprovedSickdays = totalApprovedSickdays;
		this.totalApprovedActivitiesPermissionDays = totalApprovedActivitiesPermissionDays;
		
		
	}

	public QuarterlyAttendanceReportDto(Long studentId,Long totalSchoolWorkingDays, Long totalDaysOfPresent, Long totalDaysOfAbsents,
			Long totalApprovedActivitiesPermissionDays, Long totalApprovedSickdays, String attendanceComplianceStatus
			) {
		this.studentId = studentId;
		this.totalSchoolWorkingDays = totalSchoolWorkingDays;
		this.totalDaysOfPresent = totalDaysOfPresent;
		this.totalDaysOfAbsents = totalDaysOfAbsents;
		this.totalApprovedActivitiesPermissionDays = totalApprovedActivitiesPermissionDays;
		this.totalApprovedSickdays = totalApprovedSickdays;
		this.attendanceComplianceStatus = attendanceComplianceStatus;
		
	}

	
	public QuarterlyAttendanceReportDto(Long studentId, Long totalDaysOfPresent, Long totalDaysOfAbsents,
			Long totalApprovedActivitiesPermissionDays, Long totalApprovedSickdays
			) {
		this.studentId = studentId;
		
		this.totalDaysOfPresent = totalDaysOfPresent;
		this.totalDaysOfAbsents = totalDaysOfAbsents;
		this.totalApprovedActivitiesPermissionDays = totalApprovedActivitiesPermissionDays;
		this.totalApprovedSickdays = totalApprovedSickdays;
//		this.attendanceComplianceStatus = attendanceComplianceStatus;
		
	}
	
	

	







	
	


	
	
	
	
	
	
}
