package com.studentmanagementsystem.api.model.custom.dailyattendance;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.studentmanagementsystem.api.util.WebServiceUtil;

public class DailyAttendanceDto {
	

	private Integer sno;
	
	private Long studentId;
	
	private String name;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern =WebServiceUtil.APP_DATE_FORMAT)
	private LocalDate dateOfBirth;
	
	private String email;
	
	private String phoneNumber;
	
	private Integer classOfStudy;
	
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern =WebServiceUtil.APP_DATE_FORMAT)
	private LocalDate attendanceDate;
	
	private String attendanceStatus;
	
	private String longApprovedSickLeaveFlag="N";
	
	private String approvedExtraCurricularActivitiesFlag = "N";
	
	private Long teacherId;
	
	private Long attendanceId;

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

	public String getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(String attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
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

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Integer getClassOfStudy() {
		return classOfStudy;
	}

	public void setClassOfStudy(Integer classOfStudy) {
		this.classOfStudy = classOfStudy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}


	public Integer getSno() {
		return sno;
	}

	public void setSno(Integer sno) {
		this.sno = sno;
	}

	public DailyAttendanceDto() {
		
	}
	
	public DailyAttendanceDto(Long studentId,String name,Integer classOfStudy,LocalDate dateOfBirth ,String email,String phoneNumber,
			LocalDate attendanceDate, String attendanceStatus,String longApprovedSickLeaveFlag,String approvedExtraCurricularActivitiesFlag ) {
		
		this.studentId = studentId;
		this.name=name;
		this.classOfStudy = classOfStudy;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.attendanceDate = attendanceDate;
		this.attendanceStatus = attendanceStatus;
		this.longApprovedSickLeaveFlag=longApprovedSickLeaveFlag;
		this.approvedExtraCurricularActivitiesFlag=approvedExtraCurricularActivitiesFlag;
		
		
	}

	public DailyAttendanceDto(Long studentId, String name) {
		
		this.studentId = studentId;
		this.name = name;
	}

	public DailyAttendanceDto(LocalDate attendanceDate, Long studentId, String name) {
		this.attendanceDate = attendanceDate;
		this.studentId = studentId;
		this.name=name;
	}

	public DailyAttendanceDto(LocalDate attendanceDate) {
		
		this.attendanceDate = attendanceDate;
	}
		

}
