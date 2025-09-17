package com.studentmanagementsystem.api.model.custom.quarterlyreport.response;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceReportDto;

public class QuarterlyAttendanceListResponse {
	
	
	private String status;
	private List<QuarterlyAttendanceReportDto> data;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<QuarterlyAttendanceReportDto> getData() {
		return data;
	}
	public void setData(List<QuarterlyAttendanceReportDto> data) {
		this.data = data;
	}
	
	
	

	
	

}
