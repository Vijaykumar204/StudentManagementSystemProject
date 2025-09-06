package com.studentmanagementsystem.api.service;


import java.util.List;

import com.studentmanagementsystem.api.model.custom.quarterlyreport.response.ComplianceAndNonComplianceListResponse;

public interface QuarterlyAttendanceReportService {

	void updateQuarterlyAttendanceReport(String quarterAndYear, List<Integer> marchend);

//	void monthEndCheck(LocalDate attendanceDate);

	ComplianceAndNonComplianceListResponse getNonComplianceStudents(String quarterAndYear);

	ComplianceAndNonComplianceListResponse getComplianceStudents(String quarterAndYear);

//	String getAll();

}
