package com.studentmanagementsystem.api.service;

import java.time.LocalDate;
import java.util.List;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceFilterDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.response.DailyAttendanceListResponse;
import com.studentmanagementsystem.api.model.custom.dailyattendance.response.MonthlyAbsenceListResponse;

public interface DailyAttendanceService {

	Response setAttandanceMultiStudents(List<DailyAttendanceDto> dailyAttendanceDto, LocalDate attendanceDate);

	DailyAttendanceListResponse getStudentAttendanceByDate(DailyAttendanceFilterDto dailyAttendanceFilterDto);

	MonthlyAbsenceListResponse getMonthlyAbsenceReport(DailyAttendanceFilterDto dailyAttendanceFilterDto);

}
