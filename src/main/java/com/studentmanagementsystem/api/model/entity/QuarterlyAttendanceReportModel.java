package com.studentmanagementsystem.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "quarterly_attendance_report")
public class QuarterlyAttendanceReportModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "QUARTER_Id")
	private Long quarterlyAttendanceId;
	
	@Column(name ="QUARTER_And_Year",length =7,nullable = false)
	private String quarterAndYear;
	
	@Column(name = "QUARTER_Tot_Working_Days",nullable = false)
	private Long totalSchoolWorkingDays;
	
	@Column(name = "QUARTER_Tot_Present",nullable = false)
	private Long totalDaysOfPresent;
	
	@Column(name = "QUARTER_Tot_Absent",nullable = false)
	private Long totalDaysOfAbsents;
	
	@Column(name = "QUARTER_Tot_Approved_Extra_Cur_Activities",nullable = false)
	private Long totalApprovedActivitiesPermissionDays;
	
	@Column(name = "QUARTER_Tot_Approved_Sick_Leave",nullable = false)
	private Long totalApprovedSickdays;
	
	@Column(name = "QUARTER_Attendance_Compliance_Status" ,length=2,nullable = false)
	private String  attendanceComplianceStatus;
	
	@Column(name="QUARTER_Attendance_Comments",length = 25)
	private String comments;
	
	 @ManyToOne
	@JoinColumn(name = "student_Id",referencedColumnName = "STU_Id",nullable = false)
	private StudentModel studentModel;

	public Long getQuarterlyAttendanceId() {
		return quarterlyAttendanceId;
	}

	public void setQuarterlyAttendanceId(Long quarterlyAttendanceId) {
		this.quarterlyAttendanceId = quarterlyAttendanceId;
	}

	public String getQuarterAndYear() {
		return quarterAndYear;
	}

	public void setQuarterAndYear(String quarterAndYear) {
		this.quarterAndYear = quarterAndYear;
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

	
	

	
	
}
