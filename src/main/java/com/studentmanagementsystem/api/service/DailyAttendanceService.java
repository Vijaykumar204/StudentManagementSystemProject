package com.studentmanagementsystem.api.service;

import java.time.LocalDate;
import java.util.List;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceFilterDto;

public interface DailyAttendanceService {

	//Response markStudentAttendance(List<DailyAttendanceDto> dailyAttendanceDto, LocalDate attendanceDate);

	Response listStudentAttendance(DailyAttendanceFilterDto dailyAttendanceFilterDto);

	Response attendanceList(DailyAttendanceFilterDto dailyAttendanceFilterDto);

	Response getMonthlyAbsenceReport(DailyAttendanceFilterDto dailyAttendanceFilterDto);

	Response monthliAttendanceList(DailyAttendanceFilterDto dailyAttendanceFilterDto);

	Response markStudentAttendance(List<DailyAttendanceDto> dailyAttendanceDto,Integer classOfStudy);

	Response saveAttendance(List<DailyAttendanceDto> dailyAttendanceDto,Integer classOfStudy);

}
