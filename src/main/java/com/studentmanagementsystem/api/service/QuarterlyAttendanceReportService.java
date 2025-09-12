package com.studentmanagementsystem.api.service;

import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceFilterDto;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.response.QuarterlyAttendanceListResponse;

public interface QuarterlyAttendanceReportService {

	QuarterlyAttendanceListResponse getQuarterlyAttendanceByStatus(QuarterlyAttendanceFilterDto quarterlyAttendanceFilterDto);

	void runQuarterlyAttendanceUpdate();

}
