package com.studentmanagementsystem.api.model.custom.teacher;

public class TeacherDto {

	private Integer sno;
	
	private Long Id;

	private String Name;

	private String PhoneNumber;

	private String Email;

	private String Role;

	private String Department;
	
	private Long createTeacher;

	

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getRole() {
		return Role;
	}

	public void setRole(String role) {
		Role = role;
	}

	public String getDepartment() {
		return Department;
	}

	public void setDepartment(String department) {
		Department = department;
	}

	

	public Integer getSno() {
		return sno;
	}

	public void setSno(Integer sno) {
		this.sno = sno;
	}
	
	public Long getCreateTeacher() {
		return createTeacher;
	}

	public void setCreateTeacher(Long createTeacher) {
		this.createTeacher = createTeacher;
	}

	public TeacherDto(Long id, String name, String email, String phoneNumber, String role, String department) {

		Id = id;
		Name = name;
		Email = email;
		PhoneNumber = phoneNumber;

		Role = role;
		Department = department;
	}

	public TeacherDto() {

	}

}
