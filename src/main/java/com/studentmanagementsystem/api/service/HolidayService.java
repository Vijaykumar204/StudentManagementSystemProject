package com.studentmanagementsystem.api.service;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.MessageResponse;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.schoolholidays.HolidayFilterDto;
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidaysDto;

public interface HolidayService {

	Response declaredHolidaysList(HolidayFilterDto filterDto);

	MessageResponse declareHolidays(List<SchoolHolidaysDto> schoolHolidaysDto);

	MessageResponse cancelHolidays(List<SchoolHolidaysDto> schoolHolidaysDto);

}
