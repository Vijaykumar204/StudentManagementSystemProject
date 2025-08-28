package com.studentmanagementsystem.api.model.custom.dailyattendance;

import java.time.LocalDate;
import java.util.List;

public class ExceedingDaysLeaveDto {
	
    private Long studentId;
    private String firstName;
    private String middleName;
    private String lastName;
    private List<LocalDate> attendanceDate;
	public ExceedingDaysLeaveDto(Long studentId, String firstName, String middleName, String lastName,
			List<LocalDate> attendanceDate) {
		super();
		this.studentId = studentId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.attendanceDate = attendanceDate;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<LocalDate> getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(List<LocalDate> attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
    
    
    

}
