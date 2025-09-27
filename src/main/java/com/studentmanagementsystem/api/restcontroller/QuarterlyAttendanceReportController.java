package com.studentmanagementsystem.api.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.service.QuarterlyAttendanceReportService;

@RestController
@RequestMapping("quarterly")
public class QuarterlyAttendanceReportController {
	
	@Autowired
	private QuarterlyAttendanceReportService quarterlyAttendanceReportService;


	/**
	 * Retrieve the list of compilance or non-compliance students for a given quarter and year.
	 *
	 * @param attendanceComplianceStatus The attendanceComplianceStatus value : True -> compliance ,false -> non compliance
	 * @param quarterAndYear The quarter and year to fetch records for (e.g., 03/2025)
	 * @param classOfStudy    The class of study for which attendance records need to be retrieved
	 * @return List of compilance or  non-compliance student records
	 * @author Vijiyakumar
	 */	
	@GetMapping("/list")
	ResponseEntity<?> quarterlyAttendanceList(@RequestBody CommonFilterDto filterDto){
		return new ResponseEntity<>(quarterlyAttendanceReportService.quarterlyAttendanceList(filterDto),HttpStatus.OK);
	}
	

	@GetMapping("/save")
	String updateQuarterlyReport(){
		 quarterlyAttendanceReportService.runQuarterlyAttendanceUpdate();
		 return "saved";
	}

}
