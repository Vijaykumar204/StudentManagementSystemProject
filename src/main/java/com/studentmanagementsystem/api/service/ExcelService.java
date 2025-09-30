package com.studentmanagementsystem.api.service;

import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceFilterDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.MarkFilterDto;

import jakarta.servlet.http.HttpServletResponse;

public interface ExcelService {

	void downloadMonthlyAttendanceReport(DailyAttendanceFilterDto dailyAttendanceFilterDto,HttpServletResponse response);

	void downloadMarkDetailReport(MarkFilterDto dailyAttendanceFilterDto, HttpServletResponse response);

	//void downloadMarkSummaryReport(CommonFilterDto dailyAttendanceFilterDto, HttpServletResponse response);

}
