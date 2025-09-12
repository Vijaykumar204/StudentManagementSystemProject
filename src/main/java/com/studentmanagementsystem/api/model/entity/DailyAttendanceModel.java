package com.studentmanagementsystem.api.model.entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
@Table(name = "daily_attendance_registration")
public class DailyAttendanceModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AT_Id")
	private Long attendanceId;

	@Column(name = "AT_Date", nullable = false)
	private LocalDate attendanceDate;

	@OneToOne
	@JoinColumn(name = "AT_Status", referencedColumnName = "CODE")
	private StudentCodeModel attendanceStatus;

	@Column(name = "AT_Approved_SickLeave", length = 1,columnDefinition ="CHAR DEFAULT 'N'")
	private String longApprovedSickLeaveFlag;

	@Column(name = "AT_Approved_Extra_Cur_Activities", length = 1,columnDefinition ="CHAR DEFAULT 'N'")
	private String approvedExtraCurricularActivitiesFlag;

	@Column(name = "AT_Create_Date_Time")
	private LocalDateTime createDate;

	@Column(name = "AT_Update_Teacher")
	private Long updateTeacher;

	@Column(name = "AT_Update_Date_Time")
	private LocalDateTime updateTime;

	@ManyToOne
	@JoinColumn(name = "student_Id", referencedColumnName = "STU_Id", nullable = false)
	private StudentModel studentModel;
	
	@ManyToOne
	@JoinColumn(name ="AT_Create_Teacher" ,referencedColumnName = "TEACH_Id",nullable = false)
	private TeacherModel teacherModel;

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

	public StudentCodeModel getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(StudentCodeModel attendanceStatus2) {
		this.attendanceStatus = attendanceStatus2;
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

	public StudentModel getStudentModel() {
		return studentModel;
	}

	public void setStudentModel(StudentModel studentModel) {
		this.studentModel = studentModel;
	}



	public TeacherModel getTeacherModel() {
		return teacherModel;
	}

	public void setTeacherModel(TeacherModel teacherModel) {
		this.teacherModel = teacherModel;
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