package com.studentmanagementsystem.api.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studentmanagementsystem.api.model.custom.studentmarks.ComplianceStudentWithPassOrFail;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.service.StudentMarksService;

@RestController
@RequestMapping(value = "mark")
public class StudentMarksController {
	
	@Autowired
	private StudentMarksService studentMarksService;
	
	@PostMapping("/add")
	ResponseEntity<?> saveStudentMarks(@RequestBody List<StudentMarksDto> studentMarksDto){
		return new ResponseEntity<>(studentMarksService.saveStudentMarks(studentMarksDto),HttpStatus.OK);
	}
	
	
	@GetMapping("/listCompilance")
	ResponseEntity<List<ComplianceStudentWithPassOrFail>> getAllComplianceStudentPassOrFail(@RequestParam String quarterAndYear){
		return new ResponseEntity<List<ComplianceStudentWithPassOrFail>>(studentMarksService.getAllComplianceStudentPassOrFail(quarterAndYear),HttpStatus.OK);
	}
	
	

}
