package com.studentmanagementsystem.api.model.custom.dailyattendance;

public class AbsentMailDto {
	
	private Long studentId;
    private String longApprovedSickLeaveFlag;
	private String approvedExtraCurricularActivitiesFlag;
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getLongApprovedSickLeaveFlag() {
		return longApprovedSickLeaveFlag;
	}
	public void setLongApprovedSickLeaveFlag(String longApprovedSickLeaveFlag) {
		this.longApprovedSickLeaveFlag = longApprovedSickLeaveFlag;
	}
	public String getApprovedExtraCurricularActivitiesFlag() {
		return approvedExtraCurricularActivitiesFlag;
	}
	public void setApprovedExtraCurricularActivitiesFlag(String approvedExtraCurricularActivitiesFlag) {
		this.approvedExtraCurricularActivitiesFlag = approvedExtraCurricularActivitiesFlag;
	}
	
	
	
}
