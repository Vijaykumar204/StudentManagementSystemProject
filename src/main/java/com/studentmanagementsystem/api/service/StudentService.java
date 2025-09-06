package com.studentmanagementsystem.api.service;

import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.student.StudentDto;

import com.studentmanagementsystem.api.model.custom.student.response.StudentModelListResponse;

public interface StudentService {

	StudentModelListResponse listAllDetailsStudent();

	Response saveStudent(StudentDto studentDto);

	StudentModelListResponse getAllHostelStudents(String studentActiveStatus);

	StudentModelListResponse getAllDaysStudents(String studentActiveStatus);

	StudentModelListResponse getStudentsBy(Long studentId, String studentEmail, String studentPhoneNumber);

	StudentModelListResponse getByStudentStatus(String studentActiveStatus);

	Response activeOrDeactiveByStudentId(String studentActiveStatus, Long studentId, Long teacherId);

}
