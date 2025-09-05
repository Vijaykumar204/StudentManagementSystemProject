package com.studentmanagementsystem.api.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.studentmanagementsystem.api.model.custom.studentcode.StudentCodeDto;
import com.studentmanagementsystem.api.service.StudentCodeService;



@RestController
public class StudentCodeController {
	
	
@Autowired
private StudentCodeService studentCodeService;



@PostMapping("/code")
public ResponseEntity<?> addStudentCode(@RequestBody List<StudentCodeDto> studentCodeDto){
	return new ResponseEntity<>(studentCodeService.addStudentCode(studentCodeDto),HttpStatus.OK);
}
}
