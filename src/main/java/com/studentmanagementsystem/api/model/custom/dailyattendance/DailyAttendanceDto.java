package com.studentmanagementsystem.api.model.custom.dailyattendance;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.studentmanagementsystem.api.util.WebServiceUtil;

public class DailyAttendanceDto {
	
	private Long attendanceId;
	
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern =WebServiceUtil.APP_DATE_FORMAT)
	private LocalDate attendanceDate;
	
	private String attendanceStatus;
	
	private String longApprovedSickLeaveFlag="N";
	
	private String approvedExtraCurricularActivitiesFlag = "N";
	
	private Long studentId;
	
	private Long teacherId;
	
	private String firstName;
	
	private String studentMiddleName;
	
	private String studentLastName;

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
	
	public String getStudentFirstName() {
		return getFirstName();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setStudentFirstName(String studentFirstName) {
		setFirstName(studentFirstName);
	}

	public void setFirstName(String studentFirstName) {
		this.firstName = studentFirstName;
	}

	public String getStudentMiddleName() {
		return studentMiddleName;
	}

	public void setStudentMiddleName(String studentMiddleName) {
		this.studentMiddleName = studentMiddleName;
	}

	public String getStudentLastName() {
		return studentLastName;
	}

	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}
	
	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

//	public DailyAttendanceDto(Long attendanceId, LocalDate attendanceDate, char attendanceStatus,
//			char longApprovedSickLeaveFlag, char approvedExtraCurricularActivitiesFlag, Long studentId) {
//		
//		this.attendanceId = attendanceId;
//		this.attendanceDate = attendanceDate;
//		this.attendanceStatus = attendanceStatus;
//		this.longApprovedSickLeaveFlag = longApprovedSickLeaveFlag;
//		this.approvedExtraCurricularActivitiesFlag = approvedExtraCurricularActivitiesFlag;
//		this.studentId = studentId;
//	}



	public DailyAttendanceDto() {
		
	}



	public DailyAttendanceDto(LocalDate attendanceDate, String attendanceStatus, Long studentId, String studentFirstName,
			String studentMiddleName, String studentLastName) {
		
	
		this.attendanceDate = attendanceDate;
		this.attendanceStatus = attendanceStatus;
		this.studentId = studentId;
		this.firstName = studentFirstName;
		this.studentMiddleName = studentMiddleName;
		this.studentLastName = studentLastName;
		
		
	}

	public DailyAttendanceDto(Long studentId, String studentFirstName, String studentMiddleName,
			String studentLastName) {
		super();
		this.studentId = studentId;
		this.firstName = studentFirstName;
		this.studentMiddleName = studentMiddleName;
		this.studentLastName = studentLastName;
	}

	public DailyAttendanceDto(LocalDate attendanceDate, Long studentId, String studentFirstName,
			String studentMiddleName, String studentLastName) {
		this.attendanceDate = attendanceDate;
		this.studentId = studentId;
		this.firstName = studentFirstName;
		this.studentMiddleName = studentMiddleName;
		this.studentLastName = studentLastName;
	}

	public DailyAttendanceDto(LocalDate attendanceDate) {
		
		this.attendanceDate = attendanceDate;
	}
		

}
