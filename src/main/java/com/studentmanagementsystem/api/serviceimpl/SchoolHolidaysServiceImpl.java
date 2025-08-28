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
		// TODO Auto-generated method stub
		return schoolHolidaysDao.getAllHolidays(WebServiceUtil.CANCEL_HOLIDAY);
	}
	
//	@Override
//	public Object declareHolidays(SchoolHolidaysDto schoolHolidaysDto) {
//		return schoolHolidaysDao.declareHolidays(schoolHolidaysDto);
//	}
	
	
	@Override
	public Object declareHoliday(SchoolHolidaysDto schoolHolidaysDto) {
		LocalDate date = schoolHolidaysDto.getHolidayDate();
		if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
			throw new RuntimeException("the Day is Sunday,so no need to declsre");
		}
		SchoolHolidaysModel holiday;

		if (schoolHolidaysDto.getHolidayId() == null) {

			holiday = new SchoolHolidaysModel();
		} else {
		  holiday = schoolHolidaysDao.findHolidayId(schoolHolidaysDto.getHolidayId());
		}
		holiday.setHolidayDate(schoolHolidaysDto.getHolidayDate());
		holiday.setHolidayReason(schoolHolidaysDto.getHolidayReason());
		return schoolHolidaysDao.declareHoliday(holiday);
	}
	
//	@Override
//	public Object declareMultipleHolidays(List<SchoolHolidaysDto> schoolHolidaysDto) {	
//		return schoolHolidaysDao.declareMultipleHolidays(schoolHolidaysDto);
//	}
	
	@Override
	public Object declareMultipleHolidays(List<SchoolHolidaysDto> schoolHolidaysDto) {
		
		List<SchoolHolidaysModel> holidays = new ArrayList<>();
		for (SchoolHolidaysDto schooldto : schoolHolidaysDto) {
			LocalDate date = schooldto.getHolidayDate();
			if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
				throw new RuntimeException("the Day is Sunday,so no need to declsre");
			}
			SchoolHolidaysModel holiday;

			if (schooldto.getHolidayId() == null) {

				holiday = new SchoolHolidaysModel();
			} else {
				  holiday = schoolHolidaysDao.findHolidayId(schooldto.getHolidayId());

			}
			holiday.setHolidayDate(schooldto.getHolidayDate());
			holiday.setHolidayReason(schooldto.getHolidayReason());
			holidays.add(holiday);
		}
		return schoolHolidaysDao.declareMultipleHolidays(holidays);	
	}
	
//	@Override
//	public Object cancelHolidayByDate(SchoolHolidaysDto schoolHolidaysDto) {
//		return schoolHolidaysDao.cancelHolidayByDate(schoolHolidaysDto) ;
//	}
	
	@Override
	public Object cancelHolidayByDate(SchoolHolidaysDto schoolHolidaysDto) {
		
		SchoolHolidaysModel holiday = schoolHolidaysDao.findHolidayId(schoolHolidaysDto.getHolidayDate());
		if (Boolean.TRUE.equals(holiday.getIsHolidayCancelled())) {
			throw new RuntimeException("This holiday is already cancelled");
		}

		holiday.setIsHolidayCancelled(true);
		holiday.setHolidayCancelledReason(schoolHolidaysDto.getHolidayCancelledReason());

		return schoolHolidaysDao.cancelHolidayByDate(holiday);
		
	}
	
//	@Override
//	public Object cancelMultipleHoliday(List<SchoolHolidaysDto> schoolHolidaysDto) {
//		
//		return schoolHolidaysDao.cancelMultipleHoliday(schoolHolidaysDto);
//	}
	
	@Override
	public Object cancelMultipleHoliday(List<SchoolHolidaysDto> schoolHolidaysDto) {
	
		List<SchoolHolidaysModel> holidays = new ArrayList<>();
		for (SchoolHolidaysDto schooldto : schoolHolidaysDto) {
			
			SchoolHolidaysModel holiday = schoolHolidaysDao.findHolidayId(schooldto.getHolidayDate());
			if (Boolean.TRUE.equals(holiday.getIsHolidayCancelled())) {
				throw new RuntimeException("This holiday is already cancelled");
			}

			holiday.setIsHolidayCancelled(true);
			holiday.setHolidayCancelledReason(schooldto.getHolidayCancelledReason());

			holidays.add(holiday);
		}

		return schoolHolidaysDao.cancelMultipleHoliday(holidays);
		
		
		
	}



}
