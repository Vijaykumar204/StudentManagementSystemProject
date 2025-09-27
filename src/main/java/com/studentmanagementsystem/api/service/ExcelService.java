package com.studentmanagementsystem.api.service;

import com.studentmanagementsystem.api.model.custom.CommonFilterDto;

import jakarta.servlet.http.HttpServletResponse;

public interface ExcelService {

	//void getMonthlyAbsenceStudents(CommonFilterDto dailyAttendanceFilterDto,HttpServletResponse response);

	void downloadMonthlyAttendanceReport(CommonFilterDto dailyAttendanceFilterDto,HttpServletResponse response);

	void downloadMarkDetailReport(CommonFilterDto dailyAttendanceFilterDto, HttpServletResponse response);

	void downloadMarkSummaryReport(CommonFilterDto dailyAttendanceFilterDto, HttpServletResponse response);

}
