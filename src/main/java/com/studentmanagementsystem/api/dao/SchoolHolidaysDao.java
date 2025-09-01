package com.studentmanagementsystem.api.dao;


import java.time.LocalDate;
import java.util.List;

import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidaysDto;
import com.studentmanagementsystem.api.model.entity.SchoolHolidaysModel;

public interface SchoolHolidaysDao {

	

	

//	Object cancelHolidayByDate(SchoolHolidaysDto schoolHolidaysDto);

//	Object declareMultipleHolidays(List<SchoolHolidaysDto> schoolHolidaysDto);

//	Object cancelMultipleHoliday(List<SchoolHolidaysDto> schoolHolidaysDto);

//	Object declareHolidays(SchoolHolidaysDto schoolHolidaysDto);

	List<SchoolHolidaysDto> getAllHolidays(Boolean holiday);

	SchoolHolidaysModel findHolidayId(Long holidayId);

	Object declareHoliday(SchoolHolidaysModel holiday);

	Object declareMultipleHolidays(List<SchoolHolidaysModel> holidays);

//	SchoolHolidaysModel findHolidayId(LocalDate holidayDate);

	Object cancelHolidayByDate(SchoolHolidaysModel holiday);

	Object cancelMultipleHoliday(List<SchoolHolidaysModel> holidays);

	SchoolHolidaysModel getHolidayByHolidayDate(LocalDate holidayDate);

//	SchoolHolidaysModel findByHolidayDate(LocalDate attendanceDate);


}
