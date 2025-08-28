package com.studentmanagementsystem.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "quarterly_attendance_report")
public class QuarterlyAttendanceReportModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "QA_Id")
	private Long quarterlyAttendanceId;
	
	@Column(name ="QA_And_Year")
	private String quarterAndYear;
	
	@Column(name = "QA_Tot_WorkingDays")
	private Long totalSchoolWorkingDays;
	
	@Column(name = "QA_Tot_Present")
	private Long totalDaysOfPresent;
	
	@Column(name = "QA_Tot_Absents")
	private Long totalDaysOfAbsents;
	
	@Column(name = "QA_Tot_Ap_Activities")
	private Long totalApprovedActivitiesPermissionDays;
	
	@Column(name = "QA_Tot_SickDays")
	private Long totalApprovedSickdays;
	
	@Column(name = "QA_Att_ComplianceStatus")
	private String  attendanceComplianceStatus;
	
	@Column(name="QA_Comments")
	private String comments;
	
	@OneToOne
	@JoinColumn(name = "student_Id",referencedColumnName = "STU_Id")
	private StudentModel studentModel;

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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public StudentModel getStudentModel() {
		return studentModel;
	}

	public void setStudentModel(StudentModel studentModel) {
		this.studentModel = studentModel;
	}

	public String getQuarterAndYear() {
		return quarterAndYear;
	}

	public void setQuarterAndYear(String quarterAndYear) {
		this.quarterAndYear = quarterAndYear;
	}
	
	
	


	

	
	
}
