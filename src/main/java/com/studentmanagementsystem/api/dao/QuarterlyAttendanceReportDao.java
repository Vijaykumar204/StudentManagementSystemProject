package com.studentmanagementsystem.api.dao;

import java.util.List;



import com.studentmanagementsystem.api.model.custom.quarterlyreport.ComplianceAndNonComplianceReportDto;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceReportDto;


public interface QuarterlyAttendanceReportDao {

	List<QuarterlyAttendanceReportDto> getAttendanceSummary(List<Integer> marchend);

	List<ComplianceAndNonComplianceReportDto> getNonComplianceStudents(String quarterAndYear, String complianceStatus);

	List<QuarterlyAttendanceReportDto> getQuarterlyAttendanceReport(String quarterAndResult);

}
