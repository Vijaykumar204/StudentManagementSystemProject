package com.studentmanagementsystem.api.restcontroller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;
import com.studentmanagementsystem.api.service.DailyAttendanceService;


@RestController
@RequestMapping(value = "attendance")
public class DailyAttendanceController {

	@Autowired
	private DailyAttendanceService dailyAttendanceService;


	/**
	 * Mark attendance for students.
	 *
	 * @param dailyAttendanceDto The list of attendance details of the student (from
	 *                           request body)
	 * @return Confirmation message indicating successful attendance marking
	 * @author Vijiyakumar
	 */
	@PostMapping("/save/{classOfStudy}")
	ResponseEntity<?> saveAttendance(@RequestBody List<DailyAttendanceDto> dailyAttendanceDto) {
		return new ResponseEntity<>(
				dailyAttendanceService.saveAttendance(dailyAttendanceDto), HttpStatus.OK);
	}

	/**
	 * Retrieve student attendance for a particular date 
	 *
	 *@param filterDto   Filter criteria for retrieving  attendance.
	 *                        
	 *  
	 * @return List of students attendance on the
	 *         given date
	 * @author Vijiyakumar
	 */
	@GetMapping("/list")
	ResponseEntity<?> attendanceList(@RequestBody CommonFilterDto filterDto) {
		return new ResponseEntity<>(
				dailyAttendanceService.attendanceList(filterDto),
				HttpStatus.OK);
	}


	/**
	 * Retrieve the monthly attendance list of students based on the given filters.
	 * 
	 * @param dailyAttendanceFilterDto Filter criteria for retrieving monthly attendance.
	 * @return A list of students' monthly absence records matching the given filters.
	 * @author Vijiyakumar
	 */
	@GetMapping("/month-list")
	ResponseEntity<?> monthlyAttendanceList(@RequestBody CommonFilterDto filterDto) {
		return new ResponseEntity<>(
				dailyAttendanceService.monthlyAttendanceList(filterDto),
				HttpStatus.OK);
	}
	

}
