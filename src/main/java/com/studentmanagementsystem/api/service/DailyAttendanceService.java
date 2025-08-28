package com.studentmanagementsystem.api.service;

import java.time.LocalDate;
import java.util.List;

import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.ExceedingDaysLeaveDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.MonthlyAbsenceDto;

public interface DailyAttendanceService {

	Object setAttendanceToSingleStudent(DailyAttendanceDto dailyAttendanceDto);



	


//	Object setAttandanceMultiSameDate(List<DailyAttendanceDto> dailyAttendanceDto, LocalDate attendanceDate);



	Object setAttandanceMultiStudents(List<DailyAttendanceDto> dailyAttendanceDto, LocalDate attendanceDate);






	List<DailyAttendanceDto> getStudentAttendanceByToday(LocalDate attendanceDate);






	List<DailyAttendanceDto> getStudentAttendanceNotTakeByToday(LocalDate attendanceDate);






	List<ExceedingDaysLeaveDto> getStudentleaveForExtraActivities(int month, int year);






	List<ExceedingDaysLeaveDto> getStudentleaveForSickLeave(int month, int year);






	List<MonthlyAbsenceDto> getMonthlyAbsenceStudents(int month, int year);








}
