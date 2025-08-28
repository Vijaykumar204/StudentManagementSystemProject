package com.studentmanagementsystem.api.service;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.student.StudentListRequestDto;
import com.studentmanagementsystem.api.model.custom.student.StudentSaveRequestDto;



public interface StudentService {

	List<StudentListRequestDto> listAllDetailsStudent();

	Object saveStudent(StudentSaveRequestDto studentSaveRequestDto);

	List<StudentListRequestDto> getAllHostelStudents(char studentActiveStatus);

	 List<StudentListRequestDto> getAllDaysStudents(char studentActiveStatus);

	 List<StudentListRequestDto> getStudentsBy(Long studentId, String studentEmail, String studentPhoneNumber);

	

	 List<StudentListRequestDto> getBystudentStatus(char studentActiveStatus);

	Object activeOrDeactiveByStudentId(char studentActiveStatus, Long studentId);

	

//	Object listAllStudent();


}
