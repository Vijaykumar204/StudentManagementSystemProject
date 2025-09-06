package com.studentmanagementsystem.api.dao;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.student.StudentDto;

public interface StudentModelDao {

	List<StudentDto> listAllDetailsStudent();

	List<StudentDto> getStudentsBy(Long studentId, String studentEmail, String studentPhoneNumber);

	List<StudentDto> getByStudentStatus(String studentActiveStatus);

	List<StudentDto> getAllHostelStudents(String studentActiveStatus, String hostel);

}
