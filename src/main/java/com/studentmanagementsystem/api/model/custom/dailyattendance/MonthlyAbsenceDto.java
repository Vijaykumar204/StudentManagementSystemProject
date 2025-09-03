package com.studentmanagementsystem.api.model.custom.dailyattendance;

import java.time.LocalDate;
import java.util.List;

public class MonthlyAbsenceDto {
    private Long studentId;
    private String firstName;
    private String middleName;
    private String lastName;
    private Integer totalWorkingDays;
    private Integer absentCount;
    private List<LocalDate> attendanceDate;

    public MonthlyAbsenceDto(Long studentId, String firstName, String middleName, String lastName,
    		Integer totalWorkingDays, Integer absentCount, List<LocalDate> attendanceDate) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.totalWorkingDays = totalWorkingDays;
        this.absentCount = absentCount;
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

	public Integer getTotalWorkingDays() {
		return totalWorkingDays;
	}

	public void setTotalWorkingDays(Integer totalWorkingDays) {
		this.totalWorkingDays = totalWorkingDays;
	}

	public Integer getAbsentCount() {
		return absentCount;
	}

	public void setAbsentCount(Integer absentCount) {
		this.absentCount = absentCount;
	}

	public List<LocalDate> getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(List<LocalDate> attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

    
    
}

