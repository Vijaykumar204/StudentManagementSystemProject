package com.studentmanagementsystem.api.service;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.teacher.TeacherModelListDto;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherSaveRequestDto;


public interface TeacherService {

	List<TeacherModelListDto> listAllTeachers();

	Object saveTeacher(TeacherSaveRequestDto teacherSaveRequestDto,Long teacherId);

	List<TeacherModelListDto> filterTeacher(Long teacherId, String teacherName, String teacherPhoneNumber);

}
