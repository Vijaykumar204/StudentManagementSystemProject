package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.dao.TeacherRequestDao;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherModelListDto;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherSaveRequestDto;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.service.TeacherService;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import com.studentmanagementsystem.api.validation.FieldValidation;
@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherRequestDao teacherRequestDao;
	
	@Autowired
	private FieldValidation fieldValidation;

	@Override
	public List<TeacherModelListDto> listAllTeachers() {
		// TODO Auto-generated method stub
		return teacherRequestDao.listAllTeachers();
	}

	@Override
	public Object saveTeacher(TeacherSaveRequestDto teacherSaveRequestDto,Long teacherId) {
		

		Response response = new Response();
		List<String> requestMissedFieldList = fieldValidation.checkValidationTeacherSave(teacherSaveRequestDto,teacherId);
		
		   LocalDateTime today = LocalDateTime.now();
		   

		   if (!requestMissedFieldList.isEmpty()) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(requestMissedFieldList);		
				return response;
			}
		   
		   TeacherModel teacher;
		   if(teacherSaveRequestDto.getTeacherId() == null) {
				 teacher = new TeacherModel();
				teacher.setCreateUser(teacherId);
				teacher.setCreateTime(today);	
		   }	   
		   else {		   
			   teacher = teacherRequestDao.getTeacherByTeacherId(teacherSaveRequestDto.getTeacherId());
		        
			    teacher.setUpdateUser(teacherId);
				teacher.setUpdateTime(today);

			}
			teacher.setTeacherName(teacherSaveRequestDto.getTeacherName());
			teacher.setTeacherRole(teacherSaveRequestDto.getTeacherRole());
			teacher.setTeacherDepartment(teacherSaveRequestDto.getTeacherDepartment());
			teacher.setTeacherPhoneNumber(teacherSaveRequestDto.getTeacherPhoneNumber());
		return teacherRequestDao.saveTeacher(teacher);
	}

	@Override
	public List<TeacherModelListDto> filterTeacher(Long teacherId, String teacherName, String teacherPhoneNumber) {
		
		return teacherRequestDao.filterTeacher(teacherId,teacherName,teacherPhoneNumber);
	}






}
