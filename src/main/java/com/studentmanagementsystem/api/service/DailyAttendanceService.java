package com.studentmanagementsystem.api.service;

import java.time.LocalDate;
import java.util.List;

import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.ExceedingDaysLeaveDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.MonthlyAbsenceDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.response.DailyAttendanceListResponse;
import com.studentmanagementsystem.api.model.custom.dailyattendance.response.ExceedingDaysLeaveListResponse;
import com.studentmanagementsystem.api.model.custom.dailyattendance.response.MonthlyAbsenceListResponse;

public interface DailyAttendanceService {

	Response setAttendanceToSingleStudent(DailyAttendanceDto dailyAttendanceDto);



	


//	Object setAttandanceMultiSameDate(List<DailyAttendanceDto> dailyAttendanceDto, LocalDate attendanceDate);



	Response setAttandanceMultiStudents(List<DailyAttendanceDto> dailyAttendanceDto, LocalDate attendanceDate);






	DailyAttendanceListResponse getStudentAttendanceByToday(LocalDate attendanceDate);






	DailyAttendanceListResponse getStudentAttendanceNotTakeByToday(LocalDate attendanceDate);






	 ExceedingDaysLeaveListResponse getStudentleaveForExtraActivities(int month, int year);






	 ExceedingDaysLeaveListResponse getStudentleaveForSickLeave(int month, int year);






	MonthlyAbsenceListResponse getMonthlyAbsenceStudents(int month, int year);








}
