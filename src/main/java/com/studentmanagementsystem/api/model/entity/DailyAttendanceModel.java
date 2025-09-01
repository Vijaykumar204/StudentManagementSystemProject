package com.studentmanagementsystem.api.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "daily_attendance_registration")
public class DailyAttendanceModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AT_Id")
	private Long attendanceId;
	
	@Column(name = "AT_Date",nullable = false)
	private LocalDate attendanceDate;
	
	@Column(name = "AT_Status",nullable = false)
	private char attendanceStatus;
	
	@Column(name = "AT_Approved_SickLeave",columnDefinition ="CHAR DEFAULT 'N'" )
	private char longApprovedSickLeaveFlag='N';
	
	@Column(name = "AT_Approved_Extra_Cur_Activities",columnDefinition ="CHAR DEFAULT 'N'")
	private char approvedExtraCurricularActivitiesFlag = 'N';
	
	@Column(name = "AT_Create_Teacher",nullable = false)
	private Long createTeacher;
	
	@Column(name = "AT_Create_Date_Time")
	private LocalDateTime createDate;
	
	@Column(name = "AT_Update_Teacher")
	private Long updateTeacher;
	
	@Column(name = "AT_Update_Date_Time")
	private LocalDateTime updateTime;
	
	@ManyToOne
	@JoinColumn(name = "student_Id",referencedColumnName = "STU_Id",nullable = false)
//	@JsonBackReference
	private StudentModel studentModel;

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

	public StudentModel getStudentModel() {
		return studentModel;
	}

	public void setStudentModel(StudentModel studentModel) {
		this.studentModel = studentModel;
	}

	public Long getCreateTeacher() {
		return createTeacher;
	}

	public void setCreateTeacher(Long createTeacher) {
		this.createTeacher = createTeacher;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public Long getUpdateTeacher() {
		return updateTeacher;
	}

	public void setUpdateTeacher(Long updateTeacher) {
		this.updateTeacher = updateTeacher;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
	
  
}
