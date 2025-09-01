package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.dao.TeacherRequestDao;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherModelListDto;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherSaveRequestDto;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.service.TeacherService;
import com.studentmanagementsystem.api.util.WebServiceUtil;
@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherRequestDao teacherRequestDao;

	@Override
	public List<TeacherModelListDto> listAllTeachers() {
		// TODO Auto-generated method stub
		return teacherRequestDao.listAllTeachers();
	}

	@Override
	public Object saveTeacher(TeacherSaveRequestDto teacherSaveRequestDto,Long teacherId) {
		
		final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z ]+$");
		final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10}$");
		
		List<String> validateRequestfield = new ArrayList<>();
		
		   LocalDateTime today = LocalDateTime.now();
		   
		   if(teacherSaveRequestDto.getTeacherId() == null) {
			   validateRequestfield.add(WebServiceUtil.TEACHER_ID_ERROR);
		   }
		   
		   if(teacherSaveRequestDto.getTeacherName()==null) {
			   
			   validateRequestfield.add(WebServiceUtil.NAME_ERROR);
		   }
		   else if(!NAME_PATTERN.matcher(teacherSaveRequestDto.getTeacherName()).matches()) {
			   validateRequestfield.add(WebServiceUtil.NAME_REGEX_ERROR);
		   }
		   
		   if(teacherSaveRequestDto.getTeacherPhoneNumber() == null) {
			   validateRequestfield.add(WebServiceUtil.PH_NO_ERROR);
		   }
		   else if(!PHONE_PATTERN.matcher(teacherSaveRequestDto.getTeacherPhoneNumber()).matches()) {
			   validateRequestfield.add(WebServiceUtil.PH_NO_REGEX_ERROR);
		   }
		   if(teacherSaveRequestDto.getTeacherDepartment() == null) {
			   validateRequestfield.add(WebServiceUtil.TEACHER_DEPARTMENT);
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
