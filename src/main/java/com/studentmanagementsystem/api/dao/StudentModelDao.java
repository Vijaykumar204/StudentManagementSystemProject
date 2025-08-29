package com.studentmanagementsystem.api.dao;

import java.util.List;
import java.util.Optional;

import com.studentmanagementsystem.api.model.custom.student.StudentListRequestDto;
import com.studentmanagementsystem.api.model.custom.student.StudentSaveRequestDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.entity.StudentModel;

public interface StudentModelDao {

	List<StudentListRequestDto> listAllDetailsStudent();

	

//	List<StudentListRequestDto> getAllHostelStudents(String studentActiveStatus);

//	List<StudentListRequestDto> getAllDaysStudents(String studentActiveStatus,String dayscholar);

	List<StudentListRequestDto> getStudentsBy(Long studentId, String studentEmail, String studentPhoneNumber);

	List<StudentListRequestDto> getBystudentStatus(char studentActiveStatus);

	
	List<StudentListRequestDto> getAllHostelStudents(char studentActiveStatus, char hostel);

	StudentModel getStudentModel(Long studentId);



	Optional<StudentModel> getByStudentId(Long studentId);



	Object saveStudent(StudentModel student);



	Object activeOrDeactiveByStudentId(StudentModel student);



	



//	Object listAllStudent();

}
