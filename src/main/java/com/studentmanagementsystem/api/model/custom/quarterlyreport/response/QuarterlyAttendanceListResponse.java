package com.studentmanagementsystem.api.model.custom.quarterlyreport.response;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.quarterlyreport.ComplianceAndNonComplianceReportDto;

public class QuarterlyAttendanceListResponse {
	
	
	private String status;
	private List<ComplianceAndNonComplianceReportDto> data;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<ComplianceAndNonComplianceReportDto> getData() {
		return data;
	}
	public void setData(List<ComplianceAndNonComplianceReportDto> data) {
		this.data = data;
	}
	
	

}
