package com.studentmanagementsystem.api.restcontroller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.ExceedingDaysLeaveDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.MonthlyAbsenceDto;
import com.studentmanagementsystem.api.service.DailyAttendanceService;

@RestController
@RequestMapping(value = "attendance")
public class DailyAttendanceController {
	
	@Autowired
	private DailyAttendanceService dailyAttendanceService;
	
	@PostMapping("/single")
	ResponseEntity<?> setAttendanceToSingleStudent(@RequestBody DailyAttendanceDto dailyAttendanceDto ){
		return new ResponseEntity<>(dailyAttendanceService.setAttendanceToSingleStudent(dailyAttendanceDto),HttpStatus.OK);
	}
	
//	@PostMapping("/multiesame")
//	ResponseEntity<?> setAttandanceMultiSameDate(@RequestBody List<DailyAttendanceDto> dailyAttendanceDto,@RequestParam(required = false) LocalDate attendanceDate){
//		return new ResponseEntity<>(dailyAttendanceService.setAttandanceMultiSameDate(dailyAttendanceDto,attendanceDate),HttpStatus.OK);
//		
//	}
	@PostMapping("/mulstu")
	ResponseEntity<?> setAttandanceMultiStudents(@RequestBody List<DailyAttendanceDto> dailyAttendanceDto,@RequestParam(required = false) LocalDate attendanceDate){
		return new ResponseEntity<>(dailyAttendanceService.setAttandanceMultiStudents(dailyAttendanceDto,attendanceDate),HttpStatus.OK);
		
	}
	
	@GetMapping("/todayAttendance")
	ResponseEntity<List<DailyAttendanceDto>> getStudentAttendanceByToday(@RequestParam(required = false) LocalDate attendanceDate){
		return new ResponseEntity<>(dailyAttendanceService.getStudentAttendanceByToday(attendanceDate),HttpStatus.OK);
	}
	
	@GetMapping("/todaynotAttendance")
	ResponseEntity<List<DailyAttendanceDto>> getStudentAttendanceNotTakeByToday(@RequestParam(required = false) LocalDate attendanceDate){
		return new ResponseEntity<List<DailyAttendanceDto>>(dailyAttendanceService.getStudentAttendanceNotTakeByToday(attendanceDate),HttpStatus.OK);
	}
	
	@GetMapping("/activities/{month}/{year}")
	ResponseEntity<List<ExceedingDaysLeaveDto>> getStudentleaveForExtraActivities(@PathVariable int month,@PathVariable int year){
		return new ResponseEntity<List<ExceedingDaysLeaveDto>>(dailyAttendanceService.getStudentleaveForExtraActivities(month,year),HttpStatus.OK);
	}

	@GetMapping("/sickLeave/{month}/{year}")
	ResponseEntity<List<ExceedingDaysLeaveDto>> getStudentleaveForSickLeave(@PathVariable int month,@PathVariable int year){
		return new ResponseEntity<List<ExceedingDaysLeaveDto>>(dailyAttendanceService.getStudentleaveForSickLeave(month,year),HttpStatus.OK);
	}
	
	
	@GetMapping("/monthAbsent/{month}/{year}")
	
	ResponseEntity<List<MonthlyAbsenceDto>> getMonthlyAbsenceStudents(@PathVariable int month,@PathVariable int year){
		return new ResponseEntity<List<MonthlyAbsenceDto>>(dailyAttendanceService.getMonthlyAbsenceStudents(month,year),HttpStatus.OK);
	}
}
