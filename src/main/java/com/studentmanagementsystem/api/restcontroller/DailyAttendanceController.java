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
	
	/**
	 * Mark attendance for a single student.
	 *
	 * Author: Vijayakumar
	 * @param dailyAttendanceDto The attendance details of the student (from request body)
	 * @return Confirmation message indicating successful attendance marking
	 */

	
	@PostMapping("/single")
	ResponseEntity<?> setAttendanceToSingleStudent(@RequestBody DailyAttendanceDto dailyAttendanceDto ){
		return new ResponseEntity<>(dailyAttendanceService.setAttendanceToSingleStudent(dailyAttendanceDto),HttpStatus.OK);
	}
	
	/**
	 * Mark attendance for multliple students.
	 *
	 * Author: Vijayakumar
	 * @param dailyAttendanceDto The list of  attendance details of the student (from request body)
	 * @return Confirmation message indicating successful attendance marking
	 */

	@PostMapping("/mulstu")
	ResponseEntity<?> setAttandanceMultiStudents(@RequestBody List<DailyAttendanceDto> dailyAttendanceDto,@RequestParam(required = false) LocalDate attendanceDate){
		return new ResponseEntity<>(dailyAttendanceService.setAttandanceMultiStudents(dailyAttendanceDto,attendanceDate),HttpStatus.OK);		
	}
	
	/**
	 * Retrieve student attendance.
	 * By default, retrieves today's attendance. If a date is provided, retrieves attendance for that particular date.
	 *
	 * Author: Vijayakumar
	 * @param attendanceDate (optional) The date for which to retrieve attendance
	 * @return List of student attendance records
	 */
	
	@GetMapping("/todayAttendance")
	ResponseEntity<?> getStudentAttendanceByToday(@RequestParam(required = false) LocalDate attendanceDate){
		return new ResponseEntity<>(dailyAttendanceService.getStudentAttendanceByToday(attendanceDate),HttpStatus.OK);
	}
	
	/**
	 * Retrieve students with no attendance marked.
	 * <p>
	 * By default, retrieves students without attendance for today. 
	 * If a date is provided, retrieves students without attendance for that particular date.
	 *
	 * Author: Vijayakumar
	 * @param attendanceDate (optional) The date for which to retrieve students without attendance
	 * @return List of student records without attendance
	 */
	
	
	@GetMapping("/todaynotAttendance")
	ResponseEntity<?> getStudentAttendanceNotTakeByToday(@RequestParam(required = false) LocalDate attendanceDate){
		return new ResponseEntity<>(dailyAttendanceService.getStudentAttendanceNotTakeByToday(attendanceDate),HttpStatus.OK);
	}
	
	/**
	 * Retrieve students who have taken extra-curricular activity leave 
	 * for more than 3 days in a given month.
	 *
	 * Author: Vijayakumar
	 * @param month The month for which to check leave records(path variable)
	 * @param year The year for which to check leave records(path variable)
	 * @return List of student records exceeding 3 days of extra-curricular leave
	 */
	
	
	@GetMapping("/activities/{month}/{year}")
	ResponseEntity<?> getStudentleaveForExtraActivities(@PathVariable int month,@PathVariable int year){
		return new ResponseEntity<>(dailyAttendanceService.getStudentleaveForExtraActivities(month,year),HttpStatus.OK);
	}
	
	/**
	 * Retrieve students who have taken sick leave 
	 * for more than 3 days in a given month.
	 *
	 * Author: Vijayakumar
	 * @param month The month for which to check leave records(path variable)
	 * @param year The year for which to check leave records(path variable)
	 * @return List of student records exceeding 3 days of sick leave
	 */
	

	@GetMapping("/sickLeave/{month}/{year}")
	ResponseEntity<?> getStudentleaveForSickLeave(@PathVariable int month,@PathVariable int year){
		return new ResponseEntity<>(dailyAttendanceService.getStudentleaveForSickLeave(month,year),HttpStatus.OK);
	}
	
	/**
	 * Retrieve students who were absent in a given month.
	 *
	 * Author: Vijayakumar
	 * @param month The month for which to retrieve absence records (path variable)
	 * @param year The year for which to retrieve absence records (path variable)
	 * @return List of student absence records for the specified month and year
	 */

	
	@GetMapping("/monthAbsent/{month}/{year}")
	
	ResponseEntity<?> getMonthlyAbsenceStudents(@PathVariable int month,@PathVariable int year){
		return new ResponseEntity<>(dailyAttendanceService.getMonthlyAbsenceStudents(month,year),HttpStatus.OK);
	}
}
