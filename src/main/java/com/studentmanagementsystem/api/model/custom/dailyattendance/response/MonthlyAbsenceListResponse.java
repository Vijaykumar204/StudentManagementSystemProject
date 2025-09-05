package com.studentmanagementsystem.api.model.custom.dailyattendance.response;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.dailyattendance.MonthlyAbsenceDto;

public class MonthlyAbsenceListResponse {
	
	 private String status;
		
     private List<MonthlyAbsenceDto> data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<MonthlyAbsenceDto> getData() {
		return data;
	}

	public void setData(List<MonthlyAbsenceDto> data) {
		this.data = data;
	}

     
}
