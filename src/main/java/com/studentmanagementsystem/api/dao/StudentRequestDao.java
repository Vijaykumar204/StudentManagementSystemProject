package com.studentmanagementsystem.api.dao;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.student.StudentListRequestDto;
import com.studentmanagementsystem.api.model.custom.student.StudentSaveRequestDto;

public interface StudentRequestDao {

	List<StudentListRequestDto> listAllDetailsStudent();

	Object saveStudent(StudentSaveRequestDto studentSaveRequestDto);

//	List<StudentListRequestDto> getAllHostelStudents(String studentActiveStatus);

//	List<StudentListRequestDto> getAllDaysStudents(String studentActiveStatus,String dayscholar);

	List<StudentListRequestDto> getStudentsBy(Long studentId, String studentEmail, String studentPhoneNumber);

	List<StudentListRequestDto> getBystudentStatus(char studentActiveStatus);

	Object activeOrDeactiveByStudentId(char studentActiveStatus, Long studentId);

	List<StudentListRequestDto> getAllHostelStudents(char studentActiveStatus, char hostel);

//	Object listAllStudent();

}
