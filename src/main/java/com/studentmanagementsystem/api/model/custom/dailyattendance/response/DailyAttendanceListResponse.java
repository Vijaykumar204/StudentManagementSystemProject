package com.studentmanagementsystem.api.model.custom.dailyattendance.response;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;



public class DailyAttendanceListResponse {
	
	 private String status;
		
	private Object data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}


	
	
	

}
