package com.studentmanagementsystem.api.model.custom.studentmarks.response;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.studentmarks.TotalResultCountdto;

public class TotalResultCountListResponse {

	
	private String status;
	private List<TotalResultCountdto> data;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<TotalResultCountdto> getData() {
		return data;
	}
	public void setData(List<TotalResultCountdto> data) {
		this.data = data;
	}
	
	
}
