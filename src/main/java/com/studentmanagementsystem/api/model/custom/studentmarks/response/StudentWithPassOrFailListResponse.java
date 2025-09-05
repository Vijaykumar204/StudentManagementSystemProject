package com.studentmanagementsystem.api.model.custom.studentmarks.response;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.studentmarks.StudentWithPassOrFail;

public class StudentWithPassOrFailListResponse {
	
	private String status;
	private List<StudentWithPassOrFail> data;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<StudentWithPassOrFail> getData() {
		return data;
	}
	public void setData(List<StudentWithPassOrFail> data) {
		this.data = data;
	}
	
	
	

}
