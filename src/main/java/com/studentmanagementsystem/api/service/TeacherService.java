package com.studentmanagementsystem.api.service;

import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.MessageResponse;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherDto;


public interface TeacherService {

	MessageResponse saveTeacher(TeacherDto teacherModelListDto);

	Response teacherList(CommonFilterDto filterDto);

	MessageResponse teacherLogin(String email,String password);

}
