package com.studentmanagementsystem.api.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studentmanagementsystem.api.model.custom.quarterlyreport.ComplianceAndNonComplianceReportDto;
import com.studentmanagementsystem.api.service.QuarterlyAttendanceReportService;



@RestController
@RequestMapping("quarterlyreport")
public class QuarterlyAttendanceReportController {
	
	@Autowired
	private QuarterlyAttendanceReportService quarterlyAttendanceReportService;

	
	/**
	 * Retrieve the list of non-compliance students for a given quarter and year.
	 *
	 * Author: Vijayakumar
	 * @param quarterAndYear The quarter and year to fetch records for (e.g., 03/2025)
	 * @return List of non-compliance student records
	 */
	
	@GetMapping("/noncompliance")
	ResponseEntity<?> getNonComplianceStudents(@RequestParam String quarterAndYear){
		return new ResponseEntity<>(quarterlyAttendanceReportService.getNonComplianceStudents(quarterAndYear),HttpStatus.OK);
	}
	
	/**
	 * Retrieve the list of compliance students for a given quarter and year.
	 *
	 * Author: Vijayakumar
	 * @param quarterAndYear The quarter and year to fetch records for (e.g., 03/2025)
	 * @return List of compliance student records
	 */
	
	@GetMapping("/compliance")
	ResponseEntity<?> getComplianceStudents(@RequestParam String quarterAndYear){
		return new ResponseEntity<>(quarterlyAttendanceReportService.getComplianceStudents(quarterAndYear),HttpStatus.OK);
	}


}
