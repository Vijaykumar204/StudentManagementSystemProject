package com.studentmanagementsystem.api.dao;

import java.util.List;
import java.util.Map;

import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceDto;

public interface QuarterlyAttendanceDao {

	List<QuarterlyAttendanceDto> getAttendanceSummary(List<Integer> marchend);

	List<QuarterlyAttendanceDto> getQuarterlyAttendanceReport(String quarterAndResult, Integer classOfStudy);

	Long getTotalWorkingDays(List<Integer> quarterMonthEnd);

	Map<String, Object> quarterlyAttendanceList(CommonFilterDto filterDto);

}
