package com.studentmanagementsystem.api.model.custom.dailyattendance.response;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;



public class DailyAttendanceListResponse {
	
	 private String status;
		
	private List<DailyAttendanceDto> data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<DailyAttendanceDto> getData() {
		return data;
	}

	public void setData(List<DailyAttendanceDto> data) {
		this.data = data;
	}
	
	
	

}
