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
import com.studentmanagementsystem.api.model.custom.studentmarks.markDto;
import com.studentmanagementsystem.api.service.MarkService;

@RestController
@RequestMapping(value = "mark")
public class MarkController {
	
	@Autowired
	private MarkService studentMarksService;
	
	/**
	 * Declare the marks of a student.
	 *
	 * @param studentMarksDto The student marks details (from request body)
	 * @return Confirmation message indicating that the marks were successfully declared
	 * @author Vijiyakumar
	 */
	
	@PostMapping("/save")
	ResponseEntity<?> saveStudentMarks(@RequestBody List<markDto> markDto){
		return new ResponseEntity<>(studentMarksService.saveStudentMarks(markDto),HttpStatus.OK);
	}
	
	/**
	 * Retrieve the list of all student marks for a given Filter.
	 *
	 * @param markFilterDto The Filter details (request body)
	 * @return List of all student marks based on give filter
	 * @author Vijiyakumar
	 */	
	@GetMapping("/list")
	ResponseEntity<?> listStudentMarks(@RequestBody CommonFilterDto filterDto){
		return new ResponseEntity<>(studentMarksService.listStudentMarks(filterDto),HttpStatus.OK);
	}
	
	/**
	 * Retrieve the list  result summary report.
	 *
	 * @param markFilterDto The Filter details (request body)
	 * @return List of result summary report
	 * @author Vijiyakumar
	 */	
	@GetMapping("/summary-report")
	ResponseEntity<?> resultSummaryReport(@RequestBody CommonFilterDto filterDto){
		return new ResponseEntity<>(studentMarksService.resultSummaryReport(filterDto),HttpStatus.OK);
	}
	
	
	
	

}
