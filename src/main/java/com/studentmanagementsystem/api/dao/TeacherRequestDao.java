package com.studentmanagementsystem.api.dao;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.teacher.TeacherModelListDto;

public interface TeacherRequestDao {

	List<TeacherModelListDto> listAllTeachers();

	List<TeacherModelListDto> filterTeacher(Long teacherId, String teacherName, String teacherPhoneNumber);

}
