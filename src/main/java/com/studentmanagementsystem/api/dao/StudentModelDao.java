package com.studentmanagementsystem.api.dao;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.student.StudentDto;

public interface StudentModelDao {


	List<StudentDto> getStudentsList(StudentDto studentDto);

}
