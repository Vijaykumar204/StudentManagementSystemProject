package com.studentmanagementsystem.api.service;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceFilterDto;

public interface QuarterlyAttendanceReportService {

	Response listQuarterlyAttendance(QuarterlyAttendanceFilterDto quarterlyAttendanceFilterDto);

	void runQuarterlyAttendanceUpdate();

	void findQuarterToUpadte(List<Integer> quarterMonth);

}
