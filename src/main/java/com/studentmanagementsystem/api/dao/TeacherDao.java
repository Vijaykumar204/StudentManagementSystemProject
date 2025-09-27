package com.studentmanagementsystem.api.dao;

import java.util.List;
import java.util.Map;

import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherDto;

public interface TeacherDao {

	//List<TeacherDto> listAllTeachers();

	//List<TeacherDto> filterTeacher(Long teacherId, String teacherName, String teacherPhoneNumber);

	Map<String, Object> filterTeacher(CommonFilterDto filterDto);

}
