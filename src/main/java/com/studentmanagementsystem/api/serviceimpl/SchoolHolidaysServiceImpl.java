package com.studentmanagementsystem.api.serviceimpl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.dao.SchoolHolidaysDao;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidaysDto;
import com.studentmanagementsystem.api.model.custom.schoolholidays.response.SchoolHolidayListResponse;
import com.studentmanagementsystem.api.model.entity.SchoolHolidaysModel;
import com.studentmanagementsystem.api.model.entity.TeacherModel;
import com.studentmanagementsystem.api.repository.SchoolHolidaysRepository;
import com.studentmanagementsystem.api.repository.TeacherRepository;
import com.studentmanagementsystem.api.service.SchoolHolidaysService;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import com.studentmanagementsystem.api.validation.FieldValidation;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class SchoolHolidaysServiceImpl implements SchoolHolidaysService {
	
	@Autowired
	private SchoolHolidaysDao schoolHolidaysDao;
	
	@Autowired
	private FieldValidation fieldValidation;
	
	@Autowired
	private SchoolHolidaysRepository schoolHolidaysRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;


	/**
	 *  Retrieve the list of active holidays(isCancelholiday = false).
	 */
	
	@Override
	public SchoolHolidayListResponse getHolidays() {
		SchoolHolidayListResponse response = new SchoolHolidayListResponse();
		 List<SchoolHolidaysDto> schoolHolidaysDto = schoolHolidaysDao.getAllHolidays(WebServiceUtil.HOLIDAY);
		 response.setStatus(WebServiceUtil.SUCCESS);	
			response.setData(schoolHolidaysDto);
		return response;
		
	}

	/**
	 *  Retrieve the list of cancel holidays(isCancelholiday = true)
	 */
	
	@Override
	public SchoolHolidayListResponse getCancelHolidays() {
		List<SchoolHolidaysDto> schoolHolidaysDto = schoolHolidaysDao.getAllHolidays(WebServiceUtil.CANCEL_HOLIDAY);
		SchoolHolidayListResponse response = new SchoolHolidayListResponse();
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(schoolHolidaysDto);
		return response;
	}

	/**
	 * Declare a school holiday.
	 */
	
	@Override
	public Response declareHoliday(SchoolHolidaysDto schoolHolidaysDto) {
		Response response = new Response();
		List<String> requestMissedFieldList = fieldValidation.checkValidationDeclareHoliday(schoolHolidaysDto);
		
		if (!requestMissedFieldList.isEmpty()) {
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(requestMissedFieldList);		
			return response;
		}
		
		LocalDateTime today = LocalDateTime.now();
		
		LocalDate date = schoolHolidaysDto.getHolidayDate();
		if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
			response.setStatus(WebServiceUtil.WARNING);	
  			response.setData(date + WebServiceUtil.SUNDAY);
			return response;
		}
		SchoolHolidaysModel holiday = schoolHolidaysDao.getHolidayByHolidayDate(schoolHolidaysDto.getHolidayDate()) ;

		if (holiday == null) {
			holiday = new SchoolHolidaysModel();
		}
		TeacherModel teacher = teacherRepository.findTeacherIdByTeacherId(schoolHolidaysDto.getTeacherId());
		
		if(teacher==null) {
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(WebServiceUtil.TEACHER_ID_ERROR);
			return response;
		}
		
		holiday.setHolidayDate(schoolHolidaysDto.getHolidayDate());
		holiday.setHolidayReason(schoolHolidaysDto.getHolidayReason());
		holiday.setCreateTeacher(teacher.getTeacherId());
		holiday.setCreateDate(today);
		
		
		schoolHolidaysRepository.save(holiday);
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(WebServiceUtil.SUCCESS_HOLIDAY_DECLARE);		
		return response ;
	}

	/**
	 * Declare multiple school holidays.
	 */
	
	@Override
	public Response declareMultipleHolidays(List<SchoolHolidaysDto> schoolHolidaysDto) {
		Response response = new Response();
		List<SchoolHolidaysModel> holidaysList = new ArrayList<>();
		LocalDateTime today = LocalDateTime.now();
		
		for (SchoolHolidaysDto holidaydeclare : schoolHolidaysDto) {
			
			
          List<String> requestMissedFieldList = fieldValidation.checkValidationDeclareHoliday(holidaydeclare);
			
          if (!requestMissedFieldList.isEmpty()) {
  			response.setStatus(WebServiceUtil.WARNING);	
  			response.setData(requestMissedFieldList);		
  			return response;
  		}
			LocalDate date = holidaydeclare.getHolidayDate();
			if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
				response.setStatus(WebServiceUtil.WARNING);	
	  			response.setData(date + WebServiceUtil.SUNDAY);
				return response;
				
			}
			SchoolHolidaysModel holiday = schoolHolidaysDao.getHolidayByHolidayDate(holidaydeclare.getHolidayDate()) ;

			if (holiday == null) {
				holiday = new SchoolHolidaysModel();
			}
			
			TeacherModel teacher = teacherRepository.findTeacherIdByTeacherId(holidaydeclare.getTeacherId());
			
			if(teacher==null) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(WebServiceUtil.TEACHER_ID_ERROR);
				return response;
			}
			holiday.setHolidayDate(holidaydeclare.getHolidayDate());
			holiday.setHolidayReason(holidaydeclare.getHolidayReason());
			holiday.setCreateTeacher(teacher.getTeacherId());
			holiday.setCreateDate(today);
			holidaysList.add(holiday);
		}
		schoolHolidaysRepository.saveAll(holidaysList);
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(WebServiceUtil.SUCCESS_HOLIDAY_DECLARE);
		
		return response;	
	}
	
	/**
	 * Cancel a declared holiday.
	 */
	
	@Override
	public Response cancelHolidayByDate(SchoolHolidaysDto schoolHolidaysDto) {
		Response response = new Response();
		List<String> requestMissedFieldList = fieldValidation.checkValidationCancelHolidayByDate(schoolHolidaysDto);
		LocalDateTime today = LocalDateTime.now();
		if (!requestMissedFieldList.isEmpty()) {
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(requestMissedFieldList);		
			return response;
		}
		
		SchoolHolidaysModel holiday = schoolHolidaysDao.getHolidayByHolidayDate(schoolHolidaysDto.getHolidayDate());
		if (Boolean.TRUE.equals(holiday.getIsHolidayCancelled())) {
			throw new RuntimeException(WebServiceUtil.ALREADY_HOLIDAY_CANCELLED+schoolHolidaysDto.getHolidayDate());
		}

		TeacherModel teacher = teacherRepository.findTeacherIdByTeacherId(schoolHolidaysDto.getTeacherId());
		
		if(teacher==null) {
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(WebServiceUtil.TEACHER_ID_ERROR);
			return response;
		}
		holiday.setIsHolidayCancelled(true);
		holiday.setHolidayCancelledReason(schoolHolidaysDto.getHolidayCancelledReason());
		holiday.setUpdateTeacher(teacher.getTeacherId());
		holiday.setUpdateDate(today);

		schoolHolidaysRepository.save(holiday);
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(WebServiceUtil.SUCCESS_CANCEL_HOLIDAY);
		
		return response;
		
	}
	
	/**
	 * Cancel multilple declared holiday.
	 */
	
	@Override
	public Response cancelMultipleHoliday(List<SchoolHolidaysDto> schoolHolidaysDto) {
		Response response = new Response();
		List<SchoolHolidaysModel> holidaysList = new ArrayList<>();
		LocalDateTime today = LocalDateTime.now();
		
		for (SchoolHolidaysDto holidayDeclare : schoolHolidaysDto) {
			
			List<String> requestMissedFieldList = fieldValidation.checkValidationCancelHolidayByDate(holidayDeclare);
			
			
			if (!requestMissedFieldList.isEmpty()) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(requestMissedFieldList);		
				return response;
			}
			
			SchoolHolidaysModel holiday = schoolHolidaysDao.getHolidayByHolidayDate(holidayDeclare.getHolidayDate());
			if (Boolean.TRUE.equals(holiday.getIsHolidayCancelled())) {
				throw new RuntimeException(WebServiceUtil.ALREADY_HOLIDAY_CANCELLED+holidayDeclare.getHolidayDate());
			}
			TeacherModel teacher = teacherRepository.findTeacherIdByTeacherId(holidayDeclare.getTeacherId());
			
			if(teacher==null) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(WebServiceUtil.TEACHER_ID_ERROR);
				return response;
			}

			holiday.setIsHolidayCancelled(true);
			holiday.setHolidayCancelledReason(holidayDeclare.getHolidayCancelledReason());
			holiday.setUpdateTeacher(teacher.getTeacherId());
			holiday.setUpdateDate(today);
			

			holidaysList.add(holiday);
		}
		schoolHolidaysRepository.saveAll(holidaysList);
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(WebServiceUtil.SUCCESS_CANCEL_HOLIDAY);
		
		return response;
		
		
		
	}



}
