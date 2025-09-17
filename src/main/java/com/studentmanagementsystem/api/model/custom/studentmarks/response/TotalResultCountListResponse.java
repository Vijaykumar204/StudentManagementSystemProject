package com.studentmanagementsystem.api.model.custom.studentmarks.response;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.studentmarks.ResultReport;

public class TotalResultCountListResponse {

	
	private String status;
	private List<ResultReport> data;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<ResultReport> getData() {
		return data;
	}
	public void setData(List<ResultReport> data) {
		this.data = data;
	}
	
	
}
