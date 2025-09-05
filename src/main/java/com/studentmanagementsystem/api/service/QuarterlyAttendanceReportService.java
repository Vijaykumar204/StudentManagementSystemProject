package com.studentmanagementsystem.api.service;

import java.time.LocalDate;
import java.util.List;

import com.studentmanagementsystem.api.model.custom.quarterlyreport.ComplianceAndNonComplianceReportDto;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.response.ComplianceAndNonComplianceListResponse;

public interface QuarterlyAttendanceReportService {

	void updateQuarterlyAttendanceReport(String quarterAndYear, List<Integer> marchend);

	void monthEndCheck(LocalDate attendanceDate);

	ComplianceAndNonComplianceListResponse getNonComplianceStudents(String quarterAndYear);

	ComplianceAndNonComplianceListResponse getComplianceStudents(String quarterAndYear);

//	String getAll();

}
