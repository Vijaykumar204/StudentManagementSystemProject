package com.studentmanagementsystem.api.model.entity;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "teacher")
public class TeacherModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "TEACH_Id")
	private Long  teacherId;
	
	@Column(name = "TEACH_Name",nullable = false,length=25)
	private String teacherName;
	
	@Column(name = "TEACH_Phone_Number",unique = true,nullable = false,length=10)
	private String teacherPhoneNumber;
	
//	@Column(name = "TEACH_Role",nullable = false,length=2)
	@OneToOne
	@JoinColumn(name ="TEACH_Role",referencedColumnName = "CODE" ,nullable = false)
	private StudentCodeModel teacherRole;
	
	@Column(name = "TEACH_Department",length=15,nullable = false)
	private String teacherDepartment;
	
	@Column(name = "TEACH_Create_User",nullable = false)
	private Long createUser;
	
	@Column(name = "TEACH_Create_Date_Time",nullable = false)
	private LocalDateTime createTime;
	
	@Column(name = "TEACH_Update_User")
	private Long updateUser;
	
	@Column(name = "TEACH_Update_Date_Time")
	private LocalDateTime updateTime;
	
//	@OneToMany(mappedBy = "createTeacher")
//	List<StudentModel> studentModel;
//	
//	@OneToMany(mappedBy = "updateTeacher")
//	List<StudentModel> studentModel;

//	@OneToMany(mappedBy = "teacherModel")
//	List<SchoolHolidaysModel> schoolHolidaysModel;
//	
//	@OneToMany(mappedBy = "teacherModel")
//	List<DailyAttendanceModel> dailyAttendanceModel;
	
//	@OneToMany(mappedBy = "teacherModel")
//	List<QuarterlyAttendanceReportModel> quarterlyAttendanceReportModel;
	
//	@OneToMany(mappedBy = "teacherModel")
//	List<MarkModel> markModel;
	
	
	public Long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public StudentCodeModel getTeacherRole() {
		return teacherRole;
	}
	public void setTeacherRole(StudentCodeModel teacherRole) {
		this.teacherRole = teacherRole;
	}

	public String getTeacherDepartment() {
		return teacherDepartment;
	}
	public void setTeacherDepartment(String teacherDepartment) {
		this.teacherDepartment = teacherDepartment;
	}
	public String getTeacherPhoneNumber() {
		return teacherPhoneNumber;
	}
	public void setTeacherPhoneNumber(String teacherPhoneNumber) {
		this.teacherPhoneNumber = teacherPhoneNumber;
	}
	public LocalDateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	public Long getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}
	public LocalDateTime getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}
	public TeacherModel getTeacherById(Long teacherId2) {
		
		return null;
	}


	
	
	
	
	
	
	
	
	
	

}
