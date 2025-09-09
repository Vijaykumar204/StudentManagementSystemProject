package com.studentmanagementsystem.api.dao;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.student.StudentDto;

public interface StudentModelDao {

	// List<StudentDto> listAllDetailsStudent();

	List<StudentDto> getStudentsBy(Long studentId, String email, String phoneNumber,String residingStatus,String status, Integer classOfStudy);

	//List<StudentDto> getByStudentStatus(String studentActiveStatus);

	//List<StudentDto> getAllHostelStudents(String studentActiveStatus, String hostel);

}
