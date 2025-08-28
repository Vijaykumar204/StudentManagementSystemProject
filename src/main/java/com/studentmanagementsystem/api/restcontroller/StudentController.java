package com.studentmanagementsystem.api.restcontroller;

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

import com.studentmanagementsystem.api.model.custom.student.StudentListRequestDto;
import com.studentmanagementsystem.api.model.custom.student.StudentSaveRequestDto;
import com.studentmanagementsystem.api.service.StudentService;

@RestController
@RequestMapping(value = "student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/list")
   ResponseEntity<List<StudentListRequestDto>> listAllDetailsStudent(){
	   return new ResponseEntity<>(studentService.listAllDetailsStudent(),HttpStatus.OK);
   }
	

	@PostMapping("/save")
	public ResponseEntity<?> saveStudent(@RequestBody StudentSaveRequestDto studentSaveRequestDto){
		return new ResponseEntity<>(studentService.saveStudent(studentSaveRequestDto),HttpStatus.OK);
	}

	
	@GetMapping("/hostel")
	public ResponseEntity<List<StudentListRequestDto>> getAllHostelStudents(@RequestParam char studentActiveStatus){
		return new ResponseEntity<>( studentService.getAllHostelStudents(studentActiveStatus),HttpStatus.OK);
	}
	
	@GetMapping("/dayscholars")
	
	public ResponseEntity<List<StudentListRequestDto>> getAllDaysStudents(@RequestParam char studentActiveStatus){
		return new ResponseEntity<>( studentService.getAllDaysStudents(studentActiveStatus),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<StudentListRequestDto>> getStudentsBy(@RequestParam(required = false) Long studentId ,  @RequestParam(required = false) String studentEmail,@RequestParam(required = false) String studentPhoneNumber){
		return new ResponseEntity<>( studentService.getStudentsBy(studentId,studentEmail,studentPhoneNumber),HttpStatus.OK);
	}
	
	@GetMapping("/getbystatus")
	public ResponseEntity<List<StudentListRequestDto>> getBystudentStatus(@RequestParam char studentActiveStatus){
		return new ResponseEntity<>( studentService.getBystudentStatus(studentActiveStatus),HttpStatus.OK);
	}
	
	@PostMapping("/statuschange/{studentId}")
	public ResponseEntity<?> activeOrDeactiveByStudentId(@RequestParam char studentActiveStatus,@PathVariable Long studentId){
		return new ResponseEntity<>(studentService.activeOrDeactiveByStudentId(studentActiveStatus,studentId),HttpStatus.OK);
	}
	
}
