package com.studentmanagementsystem.api.model.custom.teacher;

import java.util.List;

public class TeacherModelListResponseDto {
	
	
	private String status;
	private List<TeacherModelListDto> data;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<TeacherModelListDto> getData() {
		return data;
	}
	public void setData(List<TeacherModelListDto> data) {
		this.data = data;
	}
	
	

}
