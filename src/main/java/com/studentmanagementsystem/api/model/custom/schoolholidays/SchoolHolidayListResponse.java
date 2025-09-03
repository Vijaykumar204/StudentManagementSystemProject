package com.studentmanagementsystem.api.model.custom.schoolholidays;

import java.util.List;



public class SchoolHolidayListResponse {

    private String status;
	
	private List<SchoolHolidaysDto> data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<SchoolHolidaysDto> getData() {
		return data;
	}

	public void setData(List<SchoolHolidaysDto> data) {
		this.data = data;
	}
	
	
}
