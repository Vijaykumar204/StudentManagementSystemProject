package com.studentmanagementsystem.api.model.custom.teacher;
public class TeacherModelListDto {

	private Long teacherId;
	private String teacherName;
	private String teacherRole;
	private String teacherDepartment;
	private String teacherPhoneNumber;
//	private LocalDateTime createTime;
//	private long createUser;
//	private long updateUser;
//	private LocalDateTime updateTime;
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
	public String getTeacherRole() {
		return teacherRole;
	}
	public void setTeacherRole(String teacherRole) {
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
	public TeacherModelListDto(Long teacherId, String teacherName, String teacherRole, String teacherDepartment,
			String teacherPhoneNumber) {
		
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.teacherRole = teacherRole;
		this.teacherDepartment = teacherDepartment;
		this.teacherPhoneNumber = teacherPhoneNumber;
	}
	public TeacherModelListDto() {
	}

	
	
	
	

	
	
	
	
	
	
}
