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





	public QuarterlyAttendanceReportDto( Long totalSchoolWorkingDays, Long totalDaysOfPresent,
			Long totalDaysOfAbsents, Long totalApprovedActivitiesPermissionDays, Long totalApprovedSickdays,
			 Long studentId) {
	

		this.totalSchoolWorkingDays = totalSchoolWorkingDays;
		this.totalDaysOfPresent = totalDaysOfPresent;
		this.totalDaysOfAbsents = totalDaysOfAbsents;
		this.totalApprovedActivitiesPermissionDays = totalApprovedActivitiesPermissionDays;
		this.totalApprovedSickdays = totalApprovedSickdays;
		this.studentId = studentId;
	
	}





	public QuarterlyAttendanceReportDto(String attendanceComplianceStatus) {
		
		this.attendanceComplianceStatus = attendanceComplianceStatus;
	}

	
	


	
	
	
	
	
	
}
