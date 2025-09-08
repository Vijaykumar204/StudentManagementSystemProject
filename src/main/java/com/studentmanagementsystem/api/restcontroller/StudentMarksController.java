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

import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;

import com.studentmanagementsystem.api.service.StudentMarksService;

@RestController
@RequestMapping(value = "mark")
public class StudentMarksController {
	
	@Autowired
	private StudentMarksService studentMarksService;
	
	/**
	 * Declare the marks of a student.
	 *
	 * @param studentMarksDto The student marks details (from request body)
	 * @return Confirmation message indicating that the marks were successfully declared
	 * @author Vijiyakumar
	 */
	
	@PostMapping("/add")
	ResponseEntity<?> saveStudentMarks(@RequestBody List<StudentMarksDto> studentMarksDto){
		return new ResponseEntity<>(studentMarksService.saveStudentMarks(studentMarksDto),HttpStatus.OK);
	}
	
	/**
	 * Retrieve the list of students with their result status (pass or fail) 
	 * for a given quarter and year.
	 *
	 * @param quarterAndYear The quarter and year to fetch results for (e.g., 03/2025)
	 * @return List of students with their result status
	 * @author Vijiyakumar
	 */
	
	@GetMapping("/listCompilance")
	ResponseEntity<?> getAllComplianceStudentPassOrFail(@RequestParam String quarterAndYear){
		return new ResponseEntity<>(studentMarksService.getAllComplianceStudentPassOrFail(quarterAndYear),HttpStatus.OK);
	}
	
	/**
	 * Retrieve the list of all student marks for a given quarter and year.
	 *
	 * @param quarterAndYear The quarter and year to fetch results for (e.g., 03/2025)
	 * @return List of all student marks
	 * @author Vijiyakumar
	 */	
	@GetMapping("/list")
	ResponseEntity<?> getAllStudentMarks(@RequestParam String quarterAndYear){
		return new ResponseEntity<>(studentMarksService.getAllStudentMarks(quarterAndYear),HttpStatus.OK);
	}
	
	
	/**
	 * Retrieve the overall result summary for students in a given quarter and year.
	 *
	 * @param quarterAndYear The quarter and year to fetch results for (e.g., 03/2025)
	 * @return A summary report of student results
	 * @author Vijiyakumar
	 */
	@GetMapping("/totalList")
	ResponseEntity<?> getToatalResultCount(@RequestParam String quarterAndYear){
		return new ResponseEntity<>(studentMarksService.getToatalResultCount(quarterAndYear),HttpStatus.OK);
	}
	
	/**
	 * Retrieve the class topper in agiven quarter and Year.
	 *
	 * @param quarterAndYear The quarter and year to fetch results for (e.g., 03/2025)
	 * @return Display the class topper
	 * @author Vijiyakumar
	 */
	@GetMapping("/classTopper")
	ResponseEntity<?> getClassTopper(@RequestParam String quarterAndYear){
		return new ResponseEntity<>(studentMarksService.getClassTopper(quarterAndYear),HttpStatus.OK);
	}
	
	
	

}
