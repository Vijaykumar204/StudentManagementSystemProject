package com.studentmanagementsystem.api.dao;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.student.StudentListRequestDto;
import com.studentmanagementsystem.api.model.custom.student.StudentSaveRequestDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.entity.StudentModel;

public interface StudentRequestDao {

	List<StudentListRequestDto> listAllDetailsStudent();

	Object saveStudent(StudentSaveRequestDto studentSaveRequestDto);

//	List<StudentListRequestDto> getAllHostelStudents(String studentActiveStatus);

//	List<StudentListRequestDto> getAllDaysStudents(String studentActiveStatus,String dayscholar);

	List<StudentListRequestDto> getStudentsBy(Long studentId, String studentEmail, String studentPhoneNumber);

	List<StudentListRequestDto> getBystudentStatus(char studentActiveStatus);

	Object activeOrDeactiveByStudentId(char studentActiveStatus, Long studentId);

	List<StudentListRequestDto> getAllHostelStudents(char studentActiveStatus, char hostel);

	StudentModel getStudentModel(Long studentId);



	



//	Object listAllStudent();

}
