package com.studentmanagementsystem.api.dao;

import java.util.List;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceFilterDto;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceReportDto;

public interface QuarterlyAttendanceReportDao {

	List<QuarterlyAttendanceReportDto> getAttendanceSummary(List<Integer> marchend);

	List<QuarterlyAttendanceReportDto> getQuarterlyAttendanceReport(String quarterAndResult, Integer classOfStudy);

	Long getTotalWorkingDays(List<Integer> quarterMonthEnd);

	List<QuarterlyAttendanceReportDto> listQuarterlyAttendance(
			QuarterlyAttendanceFilterDto quarterlyAttendanceFilterDto);

}
