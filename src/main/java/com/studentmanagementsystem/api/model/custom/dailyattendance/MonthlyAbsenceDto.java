package com.studentmanagementsystem.api.model.custom.dailyattendance;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.studentmanagementsystem.api.util.WebServiceUtil;

public class MonthlyAbsenceDto {
	
	private Integer sno;
	
    private Long studentId;
    
    private String name;
    
    private Integer classOfStudy;
    
    @JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern =WebServiceUtil.APP_DATE_FORMAT)
    private LocalDate dateOfBirth;
    
    private String email;
    
    private String phoneNumber;
    
    private Long totalWorkingDays;
    
    private Long attendanceCount;
    
    @JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern =WebServiceUtil.APP_DATE_FORMAT)
    private List<LocalDate> attendanceDate;

//    public MonthlyAbsenceDto(Long studentId, String firstName, String middleName, String lastName,
//    		 Long  totalWorkingDays,
//    		 Integer absentCount, List<LocalDate> attendanceDate) {
//        this.studentId = studentId;
//        this.firstName = firstName;
//        this.middleName = middleName;
//        this.lastName = lastName;
//        this.totalWorkingDays = totalWorkingDays;
//        this.absentCount = absentCount;
//        this.attendanceDate = attendanceDate;
//    }
//
//	public MonthlyAbsenceDto(Long studentId, String firstName, String middleName, String lastName,
//			List<LocalDate> attendanceDate) {
//		
//		this.studentId = studentId;
//		this.firstName = firstName;
//		this.middleName = middleName;
//		this.lastName = lastName;
//		this.attendanceDate = attendanceDate;
//	}

	public Integer getSno() {
		return sno;
	}

	public void setSno(Integer sno) {
		this.sno = sno;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getClassOfStudy() {
		return classOfStudy;
	}

	public void setClassOfStudy(Integer classOfStudy) {
		this.classOfStudy = classOfStudy;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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

	public Long getTotalWorkingDays() {
		return totalWorkingDays;
	}

	public void setTotalWorkingDays(Long totalWorkingDays) {
		this.totalWorkingDays = totalWorkingDays;
	}



	public Long getAttendanceCount() {
		return attendanceCount;
	}

	public void setAttendanceCount(Long attendanceCount) {
		this.attendanceCount = attendanceCount;
	}

	public List<LocalDate> getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(List<LocalDate> attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public MonthlyAbsenceDto(Long studentId, String name, Integer classOfStudy, LocalDate dateOfBirth, String email,
			String phoneNumber, Long totalWorkingDays, Long attendanceCount, List<LocalDate> attendanceDate) {
		
		this.studentId = studentId;
		this.name = name;
		this.classOfStudy = classOfStudy;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.totalWorkingDays = totalWorkingDays;
		this.attendanceCount = attendanceCount;
		this.attendanceDate = attendanceDate;
	}

	public MonthlyAbsenceDto(Long studentId, String name, List<LocalDate> attendanceDate) {
		
		this.studentId = studentId;
		this.name = name;
		this.attendanceDate = attendanceDate;
	}
	
	
    




    
    
}

