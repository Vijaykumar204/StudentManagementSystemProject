package com.studentmanagementsystem.api.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.studentmanagementsystem.api.dao.TeacherDao;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.MessageResponse;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.teacher.PasswordChangeDto;
import com.studentmanagementsystem.api.model.custom.teacher.TeacherDto;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.repository.StudentCodeRespository;
import com.studentmanagementsystem.api.repository.TeacherRepository;
import com.studentmanagementsystem.api.service.TeacherService;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import com.studentmanagementsystem.api.validation.FieldValidation;
import com.studentmanagementsystem.api.validation.UniqueValidation;

import jakarta.transaction.Transactional;

@Service
public class TeacherServiceImpl implements TeacherService {
	
	private static final Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

	@Autowired
	private TeacherDao teacherDao;
	
	@Autowired
	private FieldValidation fieldValidation;
	
	@Autowired
	private UniqueValidation uniqueValidation;

	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private StudentCodeRespository studentCodeRespository;
	
	/**
	 * Save or update teacher details.
	 */
	@Override
	@Transactional
	public MessageResponse saveTeacher(TeacherDto teacherDto) {

		logger.info("Before saveTeacher : Attempting to saving the teacher for TeacherId: {}",
				teacherDto.getCreateTeacher());
		MessageResponse response = new MessageResponse();
		List<String> missedFieldList = fieldValidation.checkValidationTeacherSave(teacherDto,
				teacherDto.getCreateTeacher());
		LocalDateTime today = LocalDateTime.now();
		if (!missedFieldList.isEmpty()) {
			response.setStatus(WebServiceUtil.WARNING);
			response.setData(missedFieldList);
			return response;
		}
		TeacherModel teacherId = teacherRepository.findTeacherIdByTeacherId(teacherDto.getCreateTeacher());
		if (teacherId == null) {
			response.setStatus(WebServiceUtil.WARNING);
			response.setData(WebServiceUtil.TEACHER_ID_ERROR);
			return response;
		}
		
		TeacherModel teacher;
		if (teacherDto.getId() == null) {
			
			List<String> uniqueField =uniqueValidation.uniqueCheckTeacherSave(teacherDto);
			if(!uniqueField.isEmpty()) {
				response.setStatus(WebServiceUtil.WARNING);
				response.setData(uniqueField);
				return response;
			}
			teacher = new TeacherModel();
			teacher.setCreateUser(teacherId.getTeacherId());
			teacher.setCreateDate(today);
			teacher.setTeacherPassword(WebServiceUtil.PASSWORD);
			response.setData(String.format(WebServiceUtil.SAVE, "Teacher"));
			logger.info("After saveTeacher : Successfully saved");
		} else {
			List<String> uniqueField =uniqueValidation.uniqueCheckTeacherUpdate(teacherDto);
			if(!uniqueField.isEmpty()) {
				response.setStatus(WebServiceUtil.WARNING);
				response.setData(uniqueField);
				return response;
			}
			teacher = teacherRepository.findByTeacherId(teacherDto.getId());
			teacher.setUpdateUser(teacherId.getTeacherId());
			teacher.setUpdateDate(today);
			response.setData(String.format(WebServiceUtil.UPDATE, "Teacher"));
			logger.info("After saveTeacher : Successfully updated");
		}
		teacher.setTeacherName(teacherDto.getName());
		teacher.setTeacherRole(studentCodeRespository.findByCode(teacherDto.getRole()));
		teacher.setTeacherEmail(teacherDto.getEmail());
		teacher.setTeacherDepartment(teacherDto.getDepartment());
		teacher.setTeacherPhoneNumber(teacherDto.getPhoneNumber());
		teacherRepository.save(teacher);
		response.setStatus(WebServiceUtil.SUCCESS);
		return response;
	}

/*
 * Retrive list of teacher
 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Response teacherList(CommonFilterDto filterDto) {
		logger.info("Before teacherList : Attempting to retrive list of teachers");
		Response response = new Response();
		Map<String, Object> result = teacherDao.filterTeacher(filterDto);

		List<TeacherDto> teacherList = (List<TeacherDto>) result.get("data");
		    
			if (teacherList != null) {

				if (WebServiceUtil.S_NO.equals(filterDto.getOrderColumn())
						&& WebServiceUtil.DESCENDING_ORDER.equals(filterDto.getOrderType())) {
					int sno = teacherList.size();
					for (TeacherDto teacher : teacherList) {
						teacher.setSno(sno--);
					}
				} else {
					int sno = 1;
					for (TeacherDto teacher : teacherList) {
						teacher.setSno(sno++);
					}
				}
			}
			Long totalCount = teacherRepository.findTotalCount();
			response.setStatus(WebServiceUtil.SUCCESS);
			response.setDraw(filterDto.getDraw());
			response.setTotalCount(totalCount);
			response.setFilterCount((Long) result.get("filterCount"));
			response.setData(teacherList);
			logger.info("After teacherList :Successfully retrived");
			return response;
	}

	@Override
	@Transactional
	public MessageResponse teacherLogin(String email,String password) {
		logger.info("Before teacherLogin : Attempting to login teacherEmail : {}",email);
		
		MessageResponse response = new MessageResponse();
		
		TeacherModel validPassword = teacherRepository.findTeacherPasswordByTeacherEmail(email);
		
		if(validPassword!=null) {
			
			if(password.equals(validPassword.getTeacherPassword())) {
				
				CommonFilterDto filterDto = new CommonFilterDto();
				filterDto.setSearchBy(WebServiceUtil.EMAIL);
				filterDto.setSearchValue(email);
				
				Map<String,Object> result = teacherDao.filterTeacher(filterDto);
				
			    response.setData(result.get("data"));
			}
			else 
				 response.setData("Invalid password");
		  }
		else
			response.setData("Invalid email");
		
		response.setStatus(WebServiceUtil.SUCCESS);
		logger.info("Before teacherLogin : Successfully login");
		return response;
	}


	@Override
	@Transactional
	public MessageResponse passwordChange(PasswordChangeDto passwordChangeDto) {
		
		logger.info("Before passwordChange : Attempting to change password teacherEmail : {}",passwordChangeDto.getEmail());
		
		MessageResponse response = new MessageResponse();
		
		TeacherModel teacher = teacherRepository.findTeacherPasswordByTeacherEmail(passwordChangeDto.getEmail());
		if(teacher!=null) {
			if(teacher.getTeacherPassword().equals(passwordChangeDto.getOldPassword())) {
				
				teacher.setTeacherPassword(passwordChangeDto.getNewPassword());
				teacherRepository.save(teacher);
				response.setData("Successfully password changed");
			}
			else {
				response.setData("Old password mismatch");
			}
		}
		else {
			response.setData("Invalid email");
		}
		response.setStatus(WebServiceUtil.SUCCESS);
		logger.info("Before passwordChange : Successfully changed");
		return response;
	}
}
