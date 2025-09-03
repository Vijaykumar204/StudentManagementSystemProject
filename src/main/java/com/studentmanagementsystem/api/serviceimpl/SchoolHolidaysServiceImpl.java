package com.studentmanagementsystem.api.serviceimpl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.dao.SchoolHolidaysDao;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidayListResponse;
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidaysDto;
import com.studentmanagementsystem.api.model.entity.SchoolHolidaysModel;

import com.studentmanagementsystem.api.service.SchoolHolidaysService;
import com.studentmanagementsystem.api.util.WebServiceUtil;
import com.studentmanagementsystem.api.validation.FieldValidation;
@Service
public class SchoolHolidaysServiceImpl implements SchoolHolidaysService {
	
	@Autowired
	private SchoolHolidaysDao schoolHolidaysDao;
	
	@Autowired
	private FieldValidation fieldValidation;


	
	@Override
	public SchoolHolidayListResponse getHolidays() {
		SchoolHolidayListResponse response = new SchoolHolidayListResponse();
		 List<SchoolHolidaysDto> schoolHolidaysDto = schoolHolidaysDao.getAllHolidays(WebServiceUtil.HOLIDAY);
		 response.setStatus(WebServiceUtil.SUCCESS);	
			response.setData(schoolHolidaysDto);
		return response;
		
	}

	@Override
	public SchoolHolidayListResponse getCancelHolidays() {
		List<SchoolHolidaysDto> schoolHolidaysDto = schoolHolidaysDao.getAllHolidays(WebServiceUtil.CANCEL_HOLIDAY);
		SchoolHolidayListResponse response = new SchoolHolidayListResponse();
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(schoolHolidaysDto);
		return response;
	}

	@Override
	public Object declareHoliday(SchoolHolidaysDto schoolHolidaysDto) {
		Response response = new Response();
		List<String> requestMissedFieldList = fieldValidation.checkValidationDeclareHoliday(schoolHolidaysDto);
		
		if (!requestMissedFieldList.isEmpty()) {
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(requestMissedFieldList);		
			return response;
		}
		
		LocalDate date = schoolHolidaysDto.getHolidayDate();
		if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
			throw new RuntimeException(date + WebServiceUtil.SUNDAY);
		}
		SchoolHolidaysModel holiday = schoolHolidaysDao.getHolidayByHolidayDate(schoolHolidaysDto.getHolidayDate()) ;

		if (holiday == null) {
			holiday = new SchoolHolidaysModel();
		} 
		holiday.setHolidayDate(schoolHolidaysDto.getHolidayDate());
		holiday.setHolidayReason(schoolHolidaysDto.getHolidayReason());
		schoolHolidaysDao.declareHoliday(holiday);
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(WebServiceUtil.SUCCESS_HOLIDAY_DECLARE);		
		return response ;
	}

	@Override
	public Object declareMultipleHolidays(List<SchoolHolidaysDto> schoolHolidaysDto) {
		Response response = new Response();
		List<SchoolHolidaysModel> holidaysList = new ArrayList<>();
		
		
		for (SchoolHolidaysDto holidaydeclare : schoolHolidaysDto) {
			
			
          List<String> requestMissedFieldList = fieldValidation.checkValidationDeclareHoliday(holidaydeclare);
			
          if (!requestMissedFieldList.isEmpty()) {
  			response.setStatus(WebServiceUtil.WARNING);	
  			response.setData(requestMissedFieldList);		
  			return response;
  		}
			LocalDate date = holidaydeclare.getHolidayDate();
			if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
				throw new RuntimeException(date + WebServiceUtil.SUNDAY);
			}
			SchoolHolidaysModel holiday = schoolHolidaysDao.getHolidayByHolidayDate(holidaydeclare.getHolidayDate()) ;

			if (holiday == null) {
				holiday = new SchoolHolidaysModel();
			}
			holiday.setHolidayDate(holidaydeclare.getHolidayDate());
			holiday.setHolidayReason(holidaydeclare.getHolidayReason());
			holidaysList.add(holiday);
		}
		schoolHolidaysDao.declareMultipleHolidays(holidaysList);
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(WebServiceUtil.SUCCESS_HOLIDAY_DECLARE);
		
		return response;	
	}
	

	
	@Override
	public Object cancelHolidayByDate(SchoolHolidaysDto schoolHolidaysDto) {
		Response response = new Response();
		List<String> requestMissedFieldList = fieldValidation.checkValidationCancelHolidayByDate(schoolHolidaysDto);

		if (!requestMissedFieldList.isEmpty()) {
			response.setStatus(WebServiceUtil.WARNING);	
			response.setData(requestMissedFieldList);		
			return response;
		}
		
		SchoolHolidaysModel holiday = schoolHolidaysDao.getHolidayByHolidayDate(schoolHolidaysDto.getHolidayDate());
		if (Boolean.TRUE.equals(holiday.getIsHolidayCancelled())) {
			throw new RuntimeException(WebServiceUtil.ALREADY_HOLIDAY_CANCELLED+schoolHolidaysDto.getHolidayDate());
		}

		holiday.setIsHolidayCancelled(true);
		holiday.setHolidayCancelledReason(schoolHolidaysDto.getHolidayCancelledReason());
		schoolHolidaysDao.cancelHolidayByDate(holiday);
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(WebServiceUtil.SUCCESS_CANCEL_HOLIDAY);
		
		return response;
		
	}
	

	
	@Override
	public Object cancelMultipleHoliday(List<SchoolHolidaysDto> schoolHolidaysDto) {
		Response response = new Response();
		List<SchoolHolidaysModel> holidaysList = new ArrayList<>();

		
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

			holiday.setIsHolidayCancelled(true);
			holiday.setHolidayCancelledReason(holidayDeclare.getHolidayCancelledReason());

			holidaysList.add(holiday);
		}
		
		schoolHolidaysDao.cancelMultipleHoliday(holidaysList);
		response.setStatus(WebServiceUtil.SUCCESS);	
		response.setData(WebServiceUtil.SUCCESS_CANCEL_HOLIDAY);
		
		return response;
		
		
		
	}



}
