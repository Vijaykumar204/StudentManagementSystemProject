package com.studentmanagementsystem.api.service;

import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceFilterDto;

import jakarta.servlet.http.HttpServletResponse;

public interface ExcelService {

	//void getMonthlyAbsenceStudents(DailyAttendanceFilterDto dailyAttendanceFilterDto,HttpServletResponse response);

	void downloadMonthlyAttendanceReport(DailyAttendanceFilterDto dailyAttendanceFilterDto,HttpServletResponse response);

}
