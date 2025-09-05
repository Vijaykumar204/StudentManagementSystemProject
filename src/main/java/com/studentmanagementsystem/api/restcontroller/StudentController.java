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
	 * Author: Vijiyakumar
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
	 * @param studentSaveRequestDto All content of the student (from request body)
	 * @return Confirmation message indicating saved or updated student details
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
	 * @return List of all hostel students
	 */
	
	
	@GetMapping("/hostel")
	public ResponseEntity<?> getAllHostelStudents(@RequestParam Character studentActiveStatus){
		return new ResponseEntity<>( studentService.getAllHostelStudents(studentActiveStatus),HttpStatus.OK);
	}
	
	/**
	 * Retrive all dayscholar students
	 *
	 * Author: Vijiyakumar
	 * @param studentActiveStatus
	 * @return List os all dayscholar students
	 */
	
	@GetMapping("/dayscholars")
	
	public ResponseEntity<?> getAllDaysStudents(@RequestParam Character studentActiveStatus){
		return new ResponseEntity<>( studentService.getAllDaysStudents(studentActiveStatus),HttpStatus.OK);
	}
	
	/**
	 * Filter students by ID, email, or phone number.
	 *
	 * Author: Vijayakumar
	 * @param studentId (optional) The unique ID of the student
	 * @param studentEmail (optional) The email address of the student
	 * @param studentPhoneNumber (optional) The phone number of the student
	 * @return List of students that match the given filter criteria
	 */

	
	@GetMapping
	public ResponseEntity<?> getStudentsBy(@RequestParam(required = false) Long studentId ,  @RequestParam(required = false) String studentEmail,@RequestParam(required = false) String studentPhoneNumber){
		return new ResponseEntity<>( studentService.getStudentsBy(studentId,studentEmail,studentPhoneNumber),HttpStatus.OK);
	}
	
	/**
	 * Retrieve the list of students based on active or deactive status.
	 *
	 * Author: Vijayakumar
	 * @param studentActiveStatus (The status filter to determine active or inactive students)
	 * @return List of students matching the given status
	 */

	
	
	@GetMapping("/getbystatus")
	public ResponseEntity<?> getByStudentStatus(@RequestParam Character studentActiveStatus){
		return new ResponseEntity<>( studentService.getByStudentStatus(studentActiveStatus),HttpStatus.OK);
	}
	
	/**
	 * Activate or deactivate a student by ID.
	 *
	 * Author: Vijayakumar
	 * @param studentId The ID of the student to activate/deactivate 
	 * @param studentActiveStatus The status object containing activation details)
	 * @param TeacherId The tescher Id Who is create.
	 * @return Updated student details with active/deactive status
	 */
	
	@PostMapping("/statuschange/{studentId}")
	public ResponseEntity<?> activeOrDeactiveByStudentId(@RequestParam Character studentActiveStatus,@RequestParam Long studentId,@RequestParam Long TeacherId){
		return new ResponseEntity<>(studentService.activeOrDeactiveByStudentId(studentActiveStatus,studentId,TeacherId),HttpStatus.OK);
	}
	
}
