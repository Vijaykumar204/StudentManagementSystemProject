package com.studentmanagementsystem.api.dao;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceFilterDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.MonthlyAbsenceDto;

public interface DailyAttendanceDao {

	List<DailyAttendanceDto> getStudentAttendanceTaken(DailyAttendanceFilterDto dailyAttendanceFilterDto);

	List<DailyAttendanceDto> getStudentAttendanceNotTaken(DailyAttendanceFilterDto dailyAttendanceFilterDto);

	List<MonthlyAbsenceDto> getMonthlyAbsenceStudents(DailyAttendanceFilterDto dailyAttendanceFilterDto);


}
