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
import com.studentmanagementsystem.api.model.custom.student.StudentSaveRequestDto;
import com.studentmanagementsystem.api.service.StudentService;

@RestController
@RequestMapping(value = "student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	
	/**
	 * Retrieve the list of all student details.
	 *
	 * Author: Vijayakumar
	 * @return List of all students
	 */
	
	@GetMapping("/list")
   ResponseEntity<?> listAllDetailsStudent(){
	   return new ResponseEntity<>(studentService.listAllDetailsStudent(),HttpStatus.OK);
   }
	
	/**
	 * Save or update student details.
	 *
	 * Author: Vijiyakumar
	 * @param student All content of the student (from request body)
	 * @return Saved or updated student details
	 */
	
	@PostMapping("/save")
	public ResponseEntity<?> saveStudent(@RequestBody StudentSaveRequestDto studentSaveRequestDto){
		return new ResponseEntity<>(studentService.saveStudent(studentSaveRequestDto),HttpStatus.OK);
	}
	/**
	 * Retrive all hostel students
	 *
	 * Author: Vijiyakumar
	 * @param studentActiveStatus
	 * @return Saved or updated student details
	 */
	
	
	@GetMapping("/hostel")
	public ResponseEntity<?> getAllHostelStudents(@RequestParam Character studentActiveStatus){
		return new ResponseEntity<>( studentService.getAllHostelStudents(studentActiveStatus),HttpStatus.OK);
	}
	
	@GetMapping("/dayscholars")
	
	public ResponseEntity<?> getAllDaysStudents(@RequestParam char studentActiveStatus){
		return new ResponseEntity<>( studentService.getAllDaysStudents(studentActiveStatus),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<?> getStudentsBy(@RequestParam(required = false) Long studentId ,  @RequestParam(required = false) String studentEmail,@RequestParam(required = false) String studentPhoneNumber){
		return new ResponseEntity<>( studentService.getStudentsBy(studentId,studentEmail,studentPhoneNumber),HttpStatus.OK);
	}
	
	@GetMapping("/getbystatus")
	public ResponseEntity<?> getBystudentStatus(@RequestParam char studentActiveStatus){
		return new ResponseEntity<>( studentService.getBystudentStatus(studentActiveStatus),HttpStatus.OK);
	}
	
	@PostMapping("/statuschange/{studentId}")
	public ResponseEntity<?> activeOrDeactiveByStudentId(@RequestParam Character studentActiveStatus,@PathVariable Long studentId){
		return new ResponseEntity<>(studentService.activeOrDeactiveByStudentId(studentActiveStatus,studentId),HttpStatus.OK);
	}
	
}
