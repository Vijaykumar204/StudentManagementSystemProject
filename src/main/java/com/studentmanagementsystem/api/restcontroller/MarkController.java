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
import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceFilterDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
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
	
	@PostMapping("/add")
	ResponseEntity<?> saveStudentMarks(@RequestBody List<StudentMarksDto> studentMarksDto){
		return new ResponseEntity<>(studentMarksService.saveStudentMarks(studentMarksDto),HttpStatus.OK);
	}
	
	/**
	 * Retrieve the list of all student marks for a given Filter.
	 *
	 * @param markFilterDto The Filter details (request body)
	 * @return List of all student marks based on give filter
	 * @author Vijiyakumar
	 */	
	@GetMapping("/list")
	ResponseEntity<?> listStudentMarks(@RequestBody QuarterlyAttendanceFilterDto markFilterDto){
		return new ResponseEntity<>(studentMarksService.listStudentMarks(markFilterDto),HttpStatus.OK);
	}
	
	
	
	
	

}
