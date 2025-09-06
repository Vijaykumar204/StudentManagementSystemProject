package com.studentmanagementsystem.api.dao;

import java.time.LocalDate;
import java.util.List;

import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidaysDto;
import com.studentmanagementsystem.api.model.entity.SchoolHolidaysModel;

public interface SchoolHolidaysDao {

	List<SchoolHolidaysDto> getAllHolidays(Boolean holiday);

	SchoolHolidaysModel findHolidayId(Long holidayId);

	SchoolHolidaysModel getHolidayByHolidayDate(LocalDate holidayDate);

}
