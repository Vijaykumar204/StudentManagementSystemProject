package com.studentmanagementsystem.api.model.custom.studentmarks.response;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;

public class StudentMarkListResponse {
	private String status;
	private List<StudentMarksDto> data;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<StudentMarksDto> getData() {
		return data;
	}
	public void setData(List<StudentMarksDto> data) {
		this.data = data;
	}
	
	

}
