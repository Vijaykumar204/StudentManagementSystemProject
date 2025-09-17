package com.studentmanagementsystem.api.restcontroller;

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
import com.studentmanagementsystem.api.model.custom.teacher.TeacherModelListDto;

import com.studentmanagementsystem.api.service.TeacherService;

@RestController
@RequestMapping(value = "teacher")
public class TeacherController {
	
	@Autowired
	private TeacherService teacherService;
	
	/**
	 * Retrieve the list of all teacher details.
	 *
	 * @return List of all teachers
	 * @author Vijiyakumar
	 */	
	@GetMapping("/list")
	public ResponseEntity<?>  listAllTeachers() {
		return new ResponseEntity<>( teacherService.listAllTeachers(),HttpStatus.OK);
	}
	
	/**
	 * Save or update teacher details.
	 *
	 * @param teacherModelListDto The teacher details to be saved or updated (from request body)
	 * @param teacherId The ID of the teacher who created or updated the record
	 * @return Confirmation message indicating that the teacher details were successfully saved or updated
	 * @author Vijiyakumar
	*/	
	@PostMapping("/save/{teacherId}")
	public ResponseEntity<?>  saveTeacher(@RequestBody TeacherModelListDto teacherModelListDto,@PathVariable Long teacherId ) {
		return new ResponseEntity<>( teacherService.saveTeacher(teacherModelListDto,teacherId),HttpStatus.OK);
	}
	
	
	/**
	 * Filter teacher by ID, email, or phone number.
	 *
	 * @param teacherId (optional) The unique ID of the teacher
	 * @param teacherName (optional) The email address of the teacher
	 * @param teacherPhoneNumber (optional) The phone number of the teacher
	 * @return List of teacher that match the given filter criteria
	 * @author Vijiyakumar
	 */
	@GetMapping("/filter")
	public ResponseEntity<?>  filterTeacher(@RequestParam(required = false) Long teacherId,@RequestParam(required = false) String teacherName,@RequestParam(required = false) String teacherPhoneNumber){
		return new ResponseEntity<>( teacherService.filterTeacher(teacherId,teacherName,teacherPhoneNumber),HttpStatus.OK);
	}

}
