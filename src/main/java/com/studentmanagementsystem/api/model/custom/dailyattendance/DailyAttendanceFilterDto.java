package com.studentmanagementsystem.api.model.custom.dailyattendance;

import java.time.LocalDate;

public class DailyAttendanceFilterDto {
	
	private Integer size =0;
	
	private Integer length =10;

	private Boolean attendanceMark;
	
	private String attendanceStatus;
	
	private LocalDate date;
	
	private Integer classOfStudy;
	
	private Integer month;
	
	private Integer year;
	
	private Integer range;
	
	private String monthlyAbsence;

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Boolean getAttendanceMark() {
		return attendanceMark;
	}

	public void setAttendanceMark(Boolean attendanceMark) {
		this.attendanceMark = attendanceMark;
	}
	public String getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(String attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getClassOfStudy() {
		return classOfStudy;
	}

	public void setClassOfStudy(Integer classOfStudy) {
		this.classOfStudy = classOfStudy;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		this.range = range;
	}

	public String getMonthlyAbsence() {
		return monthlyAbsence;
	}

	public void setMonthlyAbsence(String monthlyAbsence) {
		this.monthlyAbsence = monthlyAbsence;
	}
	
}
