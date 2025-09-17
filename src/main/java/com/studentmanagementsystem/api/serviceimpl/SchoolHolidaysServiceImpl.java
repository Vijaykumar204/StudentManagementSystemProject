package com.studentmanagementsystem.api.serviceimpl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.studentmanagementsystem.api.dao.SchoolHolidaysDao;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidayFilterDto;
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidaysDto;
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
	
	private static final Logger logger = LoggerFactory.getLogger(SchoolHolidaysServiceImpl.class);
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
	 *  Retrieve the list of cancel holidays(isCancelholiday = true).
	 */
	@Override
	public Response listDeclaredHolidays(SchoolHolidayFilterDto schoolHolidayFilterDto) {
		
		logger.info("Before getHolidays - Attempting to retrieve the holiday list");

		
		Response response = new Response();
		 List<SchoolHolidaysDto> schoolHolidaysDto = schoolHolidaysDao.listDeclaredHolidays(schoolHolidayFilterDto);
		 response.setStatus(WebServiceUtil.SUCCESS);	
			response.setData(schoolHolidaysDto);
		logger.info("After getHolidays -Successfully list holidays");	
		return response;
		
	}



	/**
	 * Declare  holidays.
	 */
	@Override
	public Response declareHolidays(List<SchoolHolidaysDto> schoolHolidaysDto) {
		
		logger.info("Before declareHolidays -Attempting declare the holidays");
		
		Response response = new Response();
		List<SchoolHolidaysModel> holidaysList = new ArrayList<>();
		LocalDateTime today = LocalDateTime.now();
		int updateMessage=0;
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
			TeacherModel teacher = teacherRepository.findTeacherByTeacherId(holidaydeclare.getTeacherId());	
			if(teacher==null) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(WebServiceUtil.TEACHER_ID_ERROR);
				return response;
			}
			
			SchoolHolidaysModel holiday = schoolHolidaysRepository.getHolidayByHolidayDate(holidaydeclare.getHolidayDate()) ;
			if (holiday == null) {		
				holiday = new SchoolHolidaysModel();
				holiday.setHolidayDate(holidaydeclare.getHolidayDate());
				holiday.setCreateTeacher(teacher);
				holiday.setCreateDate(today);
			}
			else {
				
				holiday.setUpdateTeacher(teacher);
				holiday.setUpdateDate(today);
				updateMessage=1;
			}
			holiday.setHolidayReason(holidaydeclare.getHolidayReason());			
			holidaysList.add(holiday);
		}
		schoolHolidaysRepository.saveAll(holidaysList);
		response.setStatus(WebServiceUtil.SUCCESS);	
		if(updateMessage==0)
		    response.setData(WebServiceUtil.SUCCESS_HOLIDAY_DECLARE);
		else
			response.setData(WebServiceUtil.SUCCESS_HOLIDAY_UPDATE);

		logger.info("After declareHolidays - Declared holiday successfully");
		return response;	
	}
	

	
	/**
	 * Cancel declared holiday.
	 */
	@Override
	public Response cancelHolidays(List<SchoolHolidaysDto> schoolHolidaysDto) {
		logger.info("Before cancelHolidays - Attempting to cancel the holiday");
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
			
			SchoolHolidaysModel holiday = schoolHolidaysRepository.getHolidayByHolidayDate(holidayDeclare.getHolidayDate());
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
			holiday.setUpdateTeacher(teacher);
			holiday.setUpdateDate(today);
			

			holidaysList.add(holiday);
		}
		schoolHolidaysRepository.saveAll(holidaysList);
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(WebServiceUtil.SUCCESS_CANCEL_HOLIDAY);
		
		logger.info("Before cancelHolidays - Holiday cancelled successfully");
		return response;
		
	}



}
