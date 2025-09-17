package com.studentmanagementsystem.api.service;

import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceFilterDto;

public interface QuarterlyAttendanceReportService {

	Response listQuarterlyAttendance(QuarterlyAttendanceFilterDto quarterlyAttendanceFilterDto);

	void runQuarterlyAttendanceUpdate();

}
