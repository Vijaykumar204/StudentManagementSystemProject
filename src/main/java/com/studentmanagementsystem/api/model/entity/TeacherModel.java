package com.studentmanagementsystem.api.model.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	
	@Column(name = "TEACH_Email",unique = true,nullable = false,length=40)
	private String teacherEmail;
	
	@Column(name = "TEACH_Department",length=15,nullable = false)
	private String teacherDepartment;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="TEACH_Role",referencedColumnName = "CODE" ,nullable = false)
	private StudentCodeModel teacherRole;
	
	@Column(name = "TEACH_Password",unique = true,nullable = false,length=16)
	private String teacherPassword;
	
	@Column(name = "TEACH_Create_User",nullable = false)
	private Long createUser;
	
	@Column(name = "TEACH_Create_Date_Time",nullable = false)
	private LocalDateTime createDate;
	
	@Column(name = "TEACH_Update_User")
	private Long updateUser;
	
	@Column(name = "TEACH_Update_Date_Time")
	private LocalDateTime updateDate;
	
	//Getter and Setter
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

	public String getTeacherEmail() {
		return teacherEmail;
	}
	public void setTeacherEmail(String teacherEmail) {
		this.teacherEmail = teacherEmail;
	}
	public String getTeacherPassword() {
		return teacherPassword;
	}
	public void setTeacherPassword(String teacherPassword) {
		this.teacherPassword = teacherPassword;
	}
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	public LocalDateTime getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}
	
	


	
	
	
	
	
	
	
	
	
	

}
