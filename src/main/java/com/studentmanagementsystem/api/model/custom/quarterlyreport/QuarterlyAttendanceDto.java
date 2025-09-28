package com.studentmanagementsystem.api.model.custom.quarterlyreport;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.studentmanagementsystem.api.util.WebServiceUtil;

public class QuarterlyAttendanceDto {

	
	private Integer sno;

    private Long studentId;
    
    private String name;
    
    @JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern =WebServiceUtil.APP_DATE_FORMAT)
    private LocalDate dateOfBirth;
	
	private String phoneNumber;
	
	private String email;
	
	 private Integer classOfStudy;
	
	private String quarterAndYear;
	
	private Long totalSchoolWorkingDays;
	

	private Long totalDaysOfPresent;
	

	private Long totalDaysOfAbsents;
	

	private Long totalApprovedActivitiesPermissionDays;
	

	private Long totalApprovedSickdays;
	

	private String  attendanceComplianceStatus;
	
	private Integer attendancePercentage;
	
	private Long quarterlyAttendanceId;

	public Long getQuarterlyAttendanceId() {
		return quarterlyAttendanceId;
	}

	public void setQuarterlyAttendanceId(Long quarterlyAttendanceId) {
		this.quarterlyAttendanceId = quarterlyAttendanceId;
	}

	public Long getTotalSchoolWorkingDays() {
		return totalSchoolWorkingDays;
	}

	public void setTotalSchoolWorkingDays(Long totalSchoolWorkingDays) {
		this.totalSchoolWorkingDays = totalSchoolWorkingDays;
	}

	public Long getTotalDaysOfPresent() {
		return totalDaysOfPresent;
	}

	public void setTotalDaysOfPresent(Long totalDaysOfPresent) {
		this.totalDaysOfPresent = totalDaysOfPresent;
	}

	public Long getTotalDaysOfAbsents() {
		return totalDaysOfAbsents;
	}

	public void setTotalDaysOfAbsents(Long totalDaysOfAbsents) {
		this.totalDaysOfAbsents = totalDaysOfAbsents;
	}

	public Long getTotalApprovedActivitiesPermissionDays() {
		return totalApprovedActivitiesPermissionDays;
	}

	public void setTotalApprovedActivitiesPermissionDays(Long totalApprovedActivitiesPermissionDays) {
		this.totalApprovedActivitiesPermissionDays = totalApprovedActivitiesPermissionDays;
	}

	public Long getTotalApprovedSickdays() {
		return totalApprovedSickdays;
	}

	public void setTotalApprovedSickdays(Long totalApprovedSickdays) {
		this.totalApprovedSickdays = totalApprovedSickdays;
	}

	public String getAttendanceComplianceStatus() {
		return attendanceComplianceStatus;
	}

	public void setAttendanceComplianceStatus(String attendanceComplianceStatus) {
		this.attendanceComplianceStatus = attendanceComplianceStatus;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public Integer getSno() {
		return sno;
	}

	public void setSno(Integer sno) {
		this.sno = sno;
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getQuarterAndYear() {
		return quarterAndYear;
	}

	public void setQuarterAndYear(String quarterAndYear) {
		this.quarterAndYear = quarterAndYear;
	}
	
	

	public Integer getAttendancePercentage() {
		return attendancePercentage;
	}

	public void setAttendancePercentage(Integer attendancePercentage) {
		this.attendancePercentage = attendancePercentage;
	}

	public Integer getClassOfStudy() {
		return classOfStudy;
	}

	public void setClassOfStudy(Integer classOfStudy) {
		this.classOfStudy = classOfStudy;
	}

	public QuarterlyAttendanceDto(Long studentId,Long totalSchoolWorkingDays, Long totalDaysOfPresent, Long totalDaysOfAbsents, Long totalApprovedSickdays,
			Long totalApprovedActivitiesPermissionDays ) {
		
		this.studentId = studentId;
		this.totalSchoolWorkingDays = totalSchoolWorkingDays;
		this.totalDaysOfPresent = totalDaysOfPresent;
		this.totalDaysOfAbsents = totalDaysOfAbsents;
		this.totalApprovedSickdays = totalApprovedSickdays;
		this.totalApprovedActivitiesPermissionDays = totalApprovedActivitiesPermissionDays;
		
		
	}

	public QuarterlyAttendanceDto(Long studentId, Long totalSchoolWorkingDays, Long totalDaysOfPresent,
			Long totalDaysOfAbsents, Long totalApprovedActivitiesPermissionDays, Long totalApprovedSickdays,
			String attendanceComplianceStatus, Integer attendancePercentage) {
		this.studentId = studentId;
		this.totalSchoolWorkingDays = totalSchoolWorkingDays;
		this.totalDaysOfPresent = totalDaysOfPresent;
		this.totalDaysOfAbsents = totalDaysOfAbsents;
		this.totalApprovedActivitiesPermissionDays = totalApprovedActivitiesPermissionDays;
		this.totalApprovedSickdays = totalApprovedSickdays;
		this.attendanceComplianceStatus = attendanceComplianceStatus;
		this.attendancePercentage = attendancePercentage;

	}

	
	public QuarterlyAttendanceDto(Long studentId, Long totalDaysOfPresent, Long totalDaysOfAbsents,
			Long totalApprovedActivitiesPermissionDays, Long totalApprovedSickdays
			) {
		this.studentId = studentId;
		
		this.totalDaysOfPresent = totalDaysOfPresent;
		this.totalDaysOfAbsents = totalDaysOfAbsents;
		this.totalApprovedActivitiesPermissionDays = totalApprovedActivitiesPermissionDays;
		this.totalApprovedSickdays = totalApprovedSickdays;
//		this.attendanceComplianceStatus = attendanceComplianceStatus;
		
	}

	public QuarterlyAttendanceDto(Long studentId, String name, Integer classOfStudy, LocalDate dateOfBirth,
			String phoneNumber, String email, String quarterAndYear, Long totalSchoolWorkingDays,
			Long totalDaysOfPresent, Long totalDaysOfAbsents, Long totalApprovedActivitiesPermissionDays,
			Long totalApprovedSickdays, String attendanceComplianceStatus,Integer attendancePercentage) {
	
		this.studentId = studentId;
		this.name = name;
		this.classOfStudy = classOfStudy;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.quarterAndYear = quarterAndYear;
		this.totalSchoolWorkingDays = totalSchoolWorkingDays;
		this.totalDaysOfPresent = totalDaysOfPresent;
		this.totalDaysOfAbsents = totalDaysOfAbsents;
		this.totalApprovedActivitiesPermissionDays = totalApprovedActivitiesPermissionDays;
		this.totalApprovedSickdays = totalApprovedSickdays;
		this.attendanceComplianceStatus = attendanceComplianceStatus;
		this.attendancePercentage = attendancePercentage;
	}
	
	

	







	
	


	
	
	
	
	
	
}
