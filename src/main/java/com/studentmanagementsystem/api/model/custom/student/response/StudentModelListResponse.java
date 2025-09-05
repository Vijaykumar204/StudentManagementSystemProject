package com.studentmanagementsystem.api.model.custom.student.response;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.student.StudentDto;

public class StudentModelListResponse {
	
    private String status;
	
	private List<StudentDto> data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<StudentDto> getData() {
		return data;
	}

	public void setData(List<StudentDto> data) {
		this.data = data;
	}
	
	

}
