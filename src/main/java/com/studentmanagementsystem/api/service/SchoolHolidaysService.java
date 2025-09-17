package com.studentmanagementsystem.api.service;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidayFilterDto;
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidaysDto;

public interface SchoolHolidaysService {

	Response listDeclaredHolidays(SchoolHolidayFilterDto schoolHolidayFilterDto);

	//Response cancelHolidayByDate(SchoolHolidaysDto schoolHolidaysDto);

	Response declareHolidays(List<SchoolHolidaysDto> schoolHolidaysDto);

	Response cancelHolidays(List<SchoolHolidaysDto> schoolHolidaysDto);

}
