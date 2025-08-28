package com.studentmanagementsystem.api.service;

import java.time.LocalDate;
import java.util.List;

import com.studentmanagementsystem.api.model.custom.quarterlyreport.ComplianceAndNonComplianceReportDto;

public interface QuarterlyAttendanceReportService {

	void updateQuarterlyAttendanceReport(String quarterAndYear, List<Integer> marchend);

	void monthEndCheck(LocalDate attendanceDate);

	List<ComplianceAndNonComplianceReportDto> getNonComplianceStudents(String quarterAndYear);

	List<ComplianceAndNonComplianceReportDto> getComplianceStudents(String quarterAndYear);

//	String getAll();

}
