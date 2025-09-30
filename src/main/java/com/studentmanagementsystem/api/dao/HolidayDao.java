package com.studentmanagementsystem.api.dao;

import java.util.Map;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.schoolholidays.HolidayFilterDto;

public interface HolidayDao {

	Map<String, Object> declaredHolidaysList(HolidayFilterDto filterDto);

}
