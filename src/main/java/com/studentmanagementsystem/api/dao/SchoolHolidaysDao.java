package com.studentmanagementsystem.api.dao;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidayFilterDto;
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidaysDto;

public interface SchoolHolidaysDao {

	List<SchoolHolidaysDto> declaredHolidaysList(SchoolHolidayFilterDto schoolHolidayFilterDto);

}
