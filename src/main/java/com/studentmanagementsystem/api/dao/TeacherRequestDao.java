package com.studentmanagementsystem.api.dao;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.teacher.TeacherModelListDto;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherSaveRequestDto;

public interface TeacherRequestDao {

	List<TeacherModelListDto> listAllTeachers();

	Object saveTeacher(TeacherSaveRequestDto teacherSaveRequestDto, Long teacherId);

	List<TeacherModelListDto> filterTeacher(Long teacherId, String teacherName, String teacherPhoneNumber);
}
