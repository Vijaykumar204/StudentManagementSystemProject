package com.studentmanagementsystem.api.service;

import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.MessageResponse;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.student.StudentDto;
import com.studentmanagementsystem.api.model.custom.student.StudentFilterDto;

public interface StudentService {

	MessageResponse saveStudent(StudentDto studentDto);

	MessageResponse activeOrDeactiveByStudentId(String status, Long studentId, Long teacherId);

	Response listStudentDetails(StudentFilterDto filterDto);

}
