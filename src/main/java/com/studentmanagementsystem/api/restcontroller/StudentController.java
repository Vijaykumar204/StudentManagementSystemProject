package com.studentmanagementsystem.api.restcontroller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studentmanagementsystem.api.model.custom.student.StudentDto;

import com.studentmanagementsystem.api.service.StudentService;

@RestController
@RequestMapping(value = "student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	/**
	 * Save or update student details.
	 *
	 * @param studentSaveRequestDto All content of the student (from request body)
	 * @return Confirmation message indicating saved or updated student details
	 * @author Vijiyakumar
	 */	
	@PostMapping("/save")
	public ResponseEntity<?> saveStudent(@RequestBody StudentDto studentDto){
		return new ResponseEntity<>(studentService.saveStudent(studentDto),HttpStatus.OK);
	}
	
	/**
	 * Filter students by ID, email, or phone number.
	 *
	 * 
	 * @param email (optional) The email address of the student
	 * @param phoneNumber (optional) The phone number of the student
	 * @param residingStatus (optional) The residential status of the student (H -> hostel,DS->Dayscholar)
	 * @param status (optional)  The status of the student (A -> Active ,D->deactive)
	 * @param classOfStudy (optional) The class of study  of the student (6-9)
	 * @return List of students that match the given filter criteria
	 * @author Vijiyakumar
	 */
	@GetMapping
	public ResponseEntity<?> getStudentsList(@RequestBody StudentDto studentDto){
		return new ResponseEntity<>( studentService.getStudentsList(studentDto),HttpStatus.OK);
	}
	
	/**
	 * Activate or deactivate a student by ID.
	 *
	 * @param studentId The ID of the student to activate/deactivate 
	 * @param studentActiveStatus The status object containing activation details)
	 * @param TeacherId The tescher Id Who is create.
	 * @return Updated student details with active/deactive status
	 * @author Vijiyakumar
	 */	
	@PostMapping("/statuschange")
	public ResponseEntity<?> activeOrDeactiveByStudentId(@RequestParam String studentActiveStatus,@RequestParam Long studentId,@RequestParam Long TeacherId){
		return new ResponseEntity<>(studentService.activeOrDeactiveByStudentId(studentActiveStatus,studentId,TeacherId),HttpStatus.OK);
	}
	
}
