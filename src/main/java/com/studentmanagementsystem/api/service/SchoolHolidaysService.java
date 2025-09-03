package com.studentmanagementsystem.api.service;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidayListResponse;
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidaysDto;

public interface SchoolHolidaysService {

	

SchoolHolidayListResponse getHolidays();

Object cancelHolidayByDate(SchoolHolidaysDto schoolHolidaysDto);

Object declareMultipleHolidays(List<SchoolHolidaysDto> schoolHolidaysDto);

Object cancelMultipleHoliday(List<SchoolHolidaysDto> schoolHolidaysDto);

Object declareHoliday(SchoolHolidaysDto schoolHolidaysDto);

SchoolHolidayListResponse getCancelHolidays();

}
