package com.studentmanagementsystem.api.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.studentmanagementsystem.api.model.custom.student.StudentDto;

import com.studentmanagementsystem.api.model.entity.StudentModel;

public interface StudentModelDao {

	List<StudentDto> listAllDetailsStudent();



	List<StudentDto> getStudentsBy(Long studentId, String studentEmail, String studentPhoneNumber);

	List<StudentDto> getByStudentStatus(char studentActiveStatus);

	
	List<StudentDto> getAllHostelStudents(char studentActiveStatus, char hostel);

	StudentModel getStudentModel(Long studentId);



	Optional<StudentModel> getByStudentId(Long studentId);



//	Object saveStudent(StudentModel student);



//	Object activeOrDeactiveByStudentId(StudentModel student);



//	StudentModel findByStudentFirstNameAndStudentMiddleNameAndStudentLastNameAndStudentDateOfBirth(
//			String studentFirstName, String studentMiddleName, String studentLastName, LocalDate studentDateOfBirth);



	



//	Object listAllStudent();

}
