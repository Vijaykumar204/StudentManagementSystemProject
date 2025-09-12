package com.studentmanagementsystem.api.model.custom.quarterlyreport;

public class QuarterlyAttendanceFilterDto {
	
	private Integer size = 0;
	    
    private Integer length =10;
    
    private Long studentId;
    
    private String email;
    
    private String phoneNumber;
     
	private String quarterAndYear;
	
	private String filter;
	
	private Integer classOfStudy;

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

	public String getQuarterAndYear() {
		return quarterAndYear;
	}

	public void setQuarterAndYear(String quarterAndYear) {
		this.quarterAndYear = quarterAndYear;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public Integer getClassOfStudy() {
		return classOfStudy;
	}

	public void setClassOfStudy(Integer classOfStudy) {
		this.classOfStudy = classOfStudy;
	}



	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
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
	
	
	

}
