package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDateTime;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.dao.TeacherRequestDao;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherModelListDto;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherModelListResponseDto;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.repository.TeacherRepository;
import com.studentmanagementsystem.api.service.TeacherService;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import com.studentmanagementsystem.api.validation.FieldValidation;
@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherRequestDao teacherRequestDao;
	
	@Autowired
	private FieldValidation fieldValidation;
	
	 
	
	@Autowired
	private TeacherRepository teacherRepository;


	/**
	 * Retrieve the list of all teacher details.
	 */
	
	@Override
	public TeacherModelListResponseDto listAllTeachers() {
		TeacherModelListResponseDto response = new TeacherModelListResponseDto();
		response.setStatus(WebServiceUtil.SUCCESS);
		response.setData(teacherRequestDao.listAllTeachers());
		return response;
	}
	
	/**
	 * Save or update teacher details.
	 */

	@Override
	public Response saveTeacher(TeacherModelListDto teacherModelListDto,Long teacherId) {
		

		Response response = new Response();
		List<String> requestMissedFieldList = fieldValidation.checkValidationTeacherSave(teacherModelListDto,teacherId);
		
		   LocalDateTime today = LocalDateTime.now();
		   

		   if (!requestMissedFieldList.isEmpty()) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(requestMissedFieldList);		
				return response;
			}
		   
		   TeacherModel teacher;
		   if(teacherModelListDto.getTeacherId() == null) {
				 teacher = new TeacherModel();
				 TeacherModel createTeacher = teacherRepository.findTeacherIdByTeacherId(teacherId);
				 if(createTeacher == null) {
						response.setStatus(WebServiceUtil.WARNING);	
						response.setData(WebServiceUtil.TEACHER_ID_ERROR);
						return response;
					} 
				teacher.setCreateUser(createTeacher.getTeacherId());
				teacher.setCreateTime(today);	
		   }	   
		   else {		   
			   teacher = teacherRepository.findTeacherByTeacherId(teacherModelListDto.getTeacherId());       
			   TeacherModel createTeacher  = teacherRepository.findTeacherIdByTeacherId(teacherId);
				if(createTeacher == null) {
					response.setStatus(WebServiceUtil.WARNING);	
					response.setData(WebServiceUtil.TEACHER_ID_ERROR);
					return response;
				}
			    teacher.setUpdateUser(createTeacher.getTeacherId());
				teacher.setUpdateTime(today);

			}
			teacher.setTeacherName(teacherModelListDto.getTeacherName());
			teacher.setTeacherRole(teacherModelListDto.getTeacherRole());
			teacher.setTeacherDepartment(teacherModelListDto.getTeacherDepartment());
			teacher.setTeacherPhoneNumber(teacherModelListDto.getTeacherPhoneNumber());
		    teacherRepository.save(teacher);
		    
		    response.setStatus(WebServiceUtil.SUCCESS);
		    response.setData(WebServiceUtil.SAVE);
		    
		    return response;
	}

	/**
	 * Filter teacher by ID, email, or phone number.
	 */
	
	@Override
	public TeacherModelListResponseDto filterTeacher(Long teacherId, String teacherName, String teacherPhoneNumber) {
		TeacherModelListResponseDto response = new TeacherModelListResponseDto();
		response.setStatus(WebServiceUtil.SUCCESS);
		response.setData(teacherRequestDao.filterTeacher(teacherId,teacherName,teacherPhoneNumber));
		return response;
	}






}
