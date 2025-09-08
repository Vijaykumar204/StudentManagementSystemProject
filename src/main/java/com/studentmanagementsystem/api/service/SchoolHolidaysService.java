package com.studentmanagementsystem.api.service;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.schoolholidays.SchoolHolidaysDto;
import com.studentmanagementsystem.api.model.custom.schoolholidays.response.SchoolHolidayListResponse;

public interface SchoolHolidaysService {

	SchoolHolidayListResponse getHolidays();

	Response cancelHolidayByDate(SchoolHolidaysDto schoolHolidaysDto);

	Response declareMultipleHolidays(List<SchoolHolidaysDto> schoolHolidaysDto);

	Response cancelMultipleHoliday(List<SchoolHolidaysDto> schoolHolidaysDto);

	Response declareHoliday(SchoolHolidaysDto schoolHolidaysDto);

	SchoolHolidayListResponse getCancelHolidays();

}
