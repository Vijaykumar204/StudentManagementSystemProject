package com.studentmanagementsystem.api.dao;

import java.time.LocalDate;
import java.util.List;

import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.ExceedingDaysLeaveDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.MonthlyAbsenceDto;
import com.studentmanagementsystem.api.model.entity.DailyAttendanceModel;

public interface DailyAttendanceDao {



//	Object setAttandanceMultiSameDate(List<DailyAttendanceDto> dailyAttendanceDto, LocalDate attendanceDate);

	Object setAttandanceMultiStudents(List<DailyAttendanceModel> attendance);

	DailyAttendanceModel createNewAttendance(Long studentId);

	DailyAttendanceModel findAttendanceById(Long attendanceId);

	Object setAttendanceToSingleStudent(DailyAttendanceModel dailyAttendance);

	List<DailyAttendanceDto> getStudentAttendance(LocalDate today);

	List<DailyAttendanceDto> getStudentAttendanceNotTakeByToday(LocalDate today);





	List<MonthlyAbsenceDto> getMonthlyAbsenceStudents(int month, int year);

	List<ExceedingDaysLeaveDto> getStudentleaveForExtraActivitiesd(int month, int year, String leaveStatus,int leaveCount);

	DailyAttendanceModel getStudentIdAndAttendanceDate(Long studentId, LocalDate attendanceDate);

}
