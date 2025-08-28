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
import com.studentmanagementsystem.api.model.custom.teacher.TeacherModelListDto;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherSaveRequestDto;
import com.studentmanagementsystem.api.service.TeacherService;

@RestController
@RequestMapping(value = "teacher")
public class TeacherController {
	
	@Autowired
	private TeacherService teacherService;
	
	@GetMapping("/list")
	public ResponseEntity<List<TeacherModelListDto>>  listAllTeachers() {
		return new ResponseEntity<>( teacherService.listAllTeachers(),HttpStatus.OK);
	}
	
	@PostMapping("/save/{teacherId}")
	public ResponseEntity<?>  saveTeacher(@RequestBody TeacherSaveRequestDto teacherSaveRequestDto,@PathVariable Long teacherId ) {
		return new ResponseEntity<>( teacherService.saveTeacher(teacherSaveRequestDto,teacherId),HttpStatus.OK);
	}
	
	@GetMapping("/filter")
	public ResponseEntity<List<TeacherModelListDto>>  filterTeacher(@RequestParam(required = false) Long teacherId,@RequestParam(required = false) String teacherName,@RequestParam(required = false) String teacherPhoneNumber){
		return new ResponseEntity<>( teacherService.filterTeacher(teacherId,teacherName,teacherPhoneNumber),HttpStatus.OK);
	}

}
