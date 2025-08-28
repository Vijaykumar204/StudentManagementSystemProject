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

	
	
	@GetMapping("/noncompliance")
	ResponseEntity<List<ComplianceAndNonComplianceReportDto>> getNonComplianceStudents(@RequestParam String quarterAndYear){
		return new ResponseEntity<List<ComplianceAndNonComplianceReportDto>>(quarterlyAttendanceReportService.getNonComplianceStudents(quarterAndYear),HttpStatus.OK);
	}
	@GetMapping("/compliance")
	ResponseEntity<List<ComplianceAndNonComplianceReportDto>> getComplianceStudents(@RequestParam String quarterAndYear){
		return new ResponseEntity<List<ComplianceAndNonComplianceReportDto>>(quarterlyAttendanceReportService.getComplianceStudents(quarterAndYear),HttpStatus.OK);
	}


}
