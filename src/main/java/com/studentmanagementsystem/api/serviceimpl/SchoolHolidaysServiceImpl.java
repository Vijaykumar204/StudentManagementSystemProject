package com.studentmanagementsystem.api.serviceimpl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.dao.SchoolHolidaysDao;
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidaysDto;
import com.studentmanagementsystem.api.model.entity.SchoolHolidaysModel;

import com.studentmanagementsystem.api.service.SchoolHolidaysService;
import com.studentmanagementsystem.api.util.WebServiceUtil;
@Service
public class SchoolHolidaysServiceImpl implements SchoolHolidaysService {
	
	@Autowired
	private SchoolHolidaysDao schoolHolidaysDao;


	
	@Override
	public List<SchoolHolidaysDto> getHolidays() {
		return schoolHolidaysDao.getAllHolidays(WebServiceUtil.HOLIDAY);
	}

	@Override
	public List<SchoolHolidaysDto> getCancelHolidays() {
		
		return schoolHolidaysDao.getAllHolidays(WebServiceUtil.CANCEL_HOLIDAY);
	}
	

	
	@Override
	public Object declareHoliday(SchoolHolidaysDto schoolHolidaysDto) {
		
		List<String> requestMissedField = new ArrayList<>();
		
		if(schoolHolidaysDto.getHolidayDate() == null) {
			requestMissedField.add(WebServiceUtil.HOL_DATE_ERROR);
		}
		if(schoolHolidaysDto.getHolidayReason() == null) {
			requestMissedField.add(WebServiceUtil.HOL_REASON_ERROR);
		}
		if (!requestMissedField.isEmpty()) {
			return requestMissedField;
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
		return schoolHolidaysDao.declareHoliday(holiday);
	}
	

	
	@Override
	public Object declareMultipleHolidays(List<SchoolHolidaysDto> schoolHolidaysDto) {
		
		List<SchoolHolidaysModel> holidays = new ArrayList<>();
		List<String> requestMissedField = new ArrayList<>();
		
		for (SchoolHolidaysDto holidaydeclare : schoolHolidaysDto) {
			
			
			if(holidaydeclare.getHolidayDate() == null) {
				requestMissedField.add(WebServiceUtil.HOL_DATE_ERROR);
			}
			if(holidaydeclare.getHolidayReason() == null) {
				requestMissedField.add(WebServiceUtil.HOL_REASON_ERROR + holidaydeclare.getHolidayDate());
			}
			if (!requestMissedField.isEmpty()) {
				return requestMissedField;
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
			holidays.add(holiday);
		}
		return schoolHolidaysDao.declareMultipleHolidays(holidays);	
	}
	

	
	@Override
	public Object cancelHolidayByDate(SchoolHolidaysDto schoolHolidaysDto) {
		
		List<String> requestMissedField = new ArrayList<>();
		
		if(schoolHolidaysDto.getHolidayDate() == null) {
			requestMissedField.add(WebServiceUtil.HOL_DATE_ERROR);
		}
		
		if(schoolHolidaysDto.getHolidayCancelledReason()==null) {
			return WebServiceUtil.HOL_CANCALLED_REASON_ERROR + schoolHolidaysDto.getHolidayDate();
		}
		if (!requestMissedField.isEmpty()) {
			return requestMissedField;
		}
		
		SchoolHolidaysModel holiday = schoolHolidaysDao.getHolidayByHolidayDate(schoolHolidaysDto.getHolidayDate());
		if (Boolean.TRUE.equals(holiday.getIsHolidayCancelled())) {
			throw new RuntimeException(WebServiceUtil.ALREADY_HOLIDAY_CANCELLED+schoolHolidaysDto.getHolidayDate());
		}

		holiday.setIsHolidayCancelled(true);
		holiday.setHolidayCancelledReason(schoolHolidaysDto.getHolidayCancelledReason());

		return schoolHolidaysDao.cancelHolidayByDate(holiday);
		
	}
	

	
	@Override
	public Object cancelMultipleHoliday(List<SchoolHolidaysDto> schoolHolidaysDto) {
	
		List<SchoolHolidaysModel> holidays = new ArrayList<>();
		List<String> requestMissedField = new ArrayList<>();
		
		for (SchoolHolidaysDto holidayDeclare : schoolHolidaysDto) {
			
			if(holidayDeclare.getHolidayDate() == null) {
				requestMissedField.add(WebServiceUtil.HOL_DATE_ERROR);
			}
			
			if(holidayDeclare.getHolidayCancelledReason()==null) {
				return WebServiceUtil.HOL_CANCALLED_REASON_ERROR + holidayDeclare.getHolidayDate();
			}
			if (!requestMissedField.isEmpty()) {
				return requestMissedField;
			}
			SchoolHolidaysModel holiday = schoolHolidaysDao.getHolidayByHolidayDate(holidayDeclare.getHolidayDate());
			if (Boolean.TRUE.equals(holiday.getIsHolidayCancelled())) {
				throw new RuntimeException(WebServiceUtil.ALREADY_HOLIDAY_CANCELLED+holidayDeclare.getHolidayDate());
			}

			holiday.setIsHolidayCancelled(true);
			holiday.setHolidayCancelledReason(holidayDeclare.getHolidayCancelledReason());

			holidays.add(holiday);
		}

		return schoolHolidaysDao.cancelMultipleHoliday(holidays);
		
		
		
	}



}
