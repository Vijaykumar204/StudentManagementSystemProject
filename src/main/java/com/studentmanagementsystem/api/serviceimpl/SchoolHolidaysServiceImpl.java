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
     * Retrive list of declared holidays
	 */
	@Override
	public Response declaredHolidaysList(SchoolHolidayFilterDto schoolHolidayFilterDto) {
		
		logger.info("Before getHolidays - Attempting to retrieve the holiday list");

		Response response = new Response();
		List<SchoolHolidaysDto> holidayList = schoolHolidaysDao.declaredHolidaysList(schoolHolidayFilterDto);
		Integer totalCount = schoolHolidaysRepository.findTotalCount();
		int sno=1;
		 for (SchoolHolidaysDto holiday : holidayList) {
			 holiday.setSno(sno++);
	        }
		response.setStatus(WebServiceUtil.SUCCESS);
		response.setFilterCount(sno-1);
		response.setTotalCount(totalCount);
		response.setData(holidayList);
		
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
		int updateFlag=0;
		
		for (SchoolHolidaysDto holidaydeclare : schoolHolidaysDto) {
			
		//Field validation
          List<String> requestFieldList = fieldValidation.holidayValidation(holidaydeclare);
          if (!requestFieldList.isEmpty()) {
  			response.setStatus(WebServiceUtil.WARNING);	
  			response.setData(requestFieldList);		
  			return response;
  		   }
          
            // Verify if holidayDate is Sunday
			LocalDate date = holidaydeclare.getHolidayDate();
			if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
				response.setStatus(WebServiceUtil.WARNING);	
	  			response.setData(date + WebServiceUtil.SUNDAY);
				return response;
			}
			
			// Verify whether the teacher ID exists
			TeacherModel teacher = teacherRepository.findTeacherByTeacherId(holidaydeclare.getTeacherId());	
			if(teacher==null) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(WebServiceUtil.TEACHER_ID_ERROR);
				return response;
			}
			//Verify whether holidayDate is already declared
			SchoolHolidaysModel holiday = schoolHolidaysRepository.getHolidayByHolidayDate(holidaydeclare.getHolidayDate()) ;
			//Declare holidays
			if (holiday == null) {		
				holiday = new SchoolHolidaysModel();
				holiday.setHolidayDate(holidaydeclare.getHolidayDate());
				holiday.setCreateTeacher(teacher);
				holiday.setCreateDate(today);
			}
			//Update holidays
			else {
				holiday.setUpdateTeacher(teacher);
				holiday.setUpdateDate(today);
				updateFlag=1;
			}
			holiday.setHolidayReason(holidaydeclare.getHolidayReason());			
			holidaysList.add(holiday);
		}
		
		schoolHolidaysRepository.saveAll(holidaysList);
		
		response.setStatus(WebServiceUtil.SUCCESS);	
		if(updateFlag==0)
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
			
			//Field validation
			List<String> requestFieldList = fieldValidation.cancelHolidayValidation(holidayDeclare);
			if (!requestFieldList.isEmpty()) {
				response.setStatus(WebServiceUtil.WARNING);	
				response.setData(requestFieldList);		
				return response;
			}
			
			// Retrieve holiday date to cancel
			SchoolHolidaysModel holiday = schoolHolidaysRepository.getHolidayByHolidayDate(holidayDeclare.getHolidayDate());
			
			// Verify if holidayDate is already cancelled
			if (Boolean.TRUE.equals(holiday.getIsHolidayCancelled())) {
				throw new RuntimeException(WebServiceUtil.ALREADY_HOLIDAY_CANCELLED+holidayDeclare.getHolidayDate());
			}
			
			// Verify whether the teacher ID exists
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
