package com.studentmanagementsystem.api.dao;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.student.StudentDto;
import com.studentmanagementsystem.api.model.custom.student.StudentFilterDto;

public interface StudentDao {


	List<StudentDto> listStudentDetails(StudentFilterDto filterDto);

}
