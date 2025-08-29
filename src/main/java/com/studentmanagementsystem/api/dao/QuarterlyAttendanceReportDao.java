package com.studentmanagementsystem.api.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.studentmanagementsystem.api.model.custom.quarterlyreport.ComplianceAndNonComplianceReportDto;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceReportDto;
import com.studentmanagementsystem.api.model.entity.QuarterlyAttendanceReportModel;
import com.studentmanagementsystem.api.model.entity.StudentModel;

@Repository
public interface QuarterlyAttendanceReportDao {

	List<QuarterlyAttendanceReportDto> getAttendanceSummary(List<Integer> marchend);

	StudentModel getStudentModelByStuId(Long studentId);

	void saveQuarterlyAttendanceReport(QuarterlyAttendanceReportModel quarterlyAttendanceReportModel);

	QuarterlyAttendanceReportModel getStudentIdUpdateReport(Long studentId, String quarterAndYear);

	List<ComplianceAndNonComplianceReportDto> getNonComplianceStudents(String quarterAndYear,
			String complianceStatus);

	String getComplianceStatus(Long studentId, String quarterAndYear);


	


	
	

}
