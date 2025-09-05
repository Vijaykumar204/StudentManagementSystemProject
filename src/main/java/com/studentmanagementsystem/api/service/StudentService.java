package com.studentmanagementsystem.api.service;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.student.StudentDto;
import com.studentmanagementsystem.api.model.custom.student.StudentSaveRequestDto;
import com.studentmanagementsystem.api.model.custom.student.response.StudentModelListResponse;



public interface StudentService {

	StudentModelListResponse listAllDetailsStudent();

	Response saveStudent(StudentSaveRequestDto studentSaveRequestDto);

	StudentModelListResponse getAllHostelStudents(char studentActiveStatus);

	 StudentModelListResponse getAllDaysStudents(char studentActiveStatus);

	 StudentModelListResponse getStudentsBy(Long studentId, String studentEmail, String studentPhoneNumber);

	

	 StudentModelListResponse getByStudentStatus(char studentActiveStatus);

	Response activeOrDeactiveByStudentId(Character studentActiveStatus, Long studentId,Long teacherId);

	

//	Object listAllStudent();


}
