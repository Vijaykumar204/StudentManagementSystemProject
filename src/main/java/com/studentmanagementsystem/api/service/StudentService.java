package com.studentmanagementsystem.api.service;

import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.student.StudentDto;

import com.studentmanagementsystem.api.model.custom.student.response.StudentModelListResponse;

public interface StudentService {

	Response saveStudent(StudentDto studentDto);

//	StudentModelListResponse getStudentsBy(Long studentId, String email, String phoneNumber, String residingStatus,
//			String status, Integer classOfStudy);

	Response activeOrDeactiveByStudentId(String studentActiveStatus, Long studentId, Long teacherId);

	StudentModelListResponse getStudentsList(StudentDto studentDto);

}
