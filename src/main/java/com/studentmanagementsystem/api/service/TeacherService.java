package com.studentmanagementsystem.api.service;


import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherModelListDto;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherModelListResponseDto;



public interface TeacherService {

	TeacherModelListResponseDto listAllTeachers();

	Response saveTeacher(TeacherModelListDto teacherModelListDto,Long teacherId);

	TeacherModelListResponseDto filterTeacher(Long teacherId, String teacherName, String teacherPhoneNumber);

}
