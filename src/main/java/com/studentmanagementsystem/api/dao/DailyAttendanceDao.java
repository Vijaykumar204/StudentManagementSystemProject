package com.studentmanagementsystem.api.dao;

import java.util.Map;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceFilterDto;

public interface DailyAttendanceDao {

	Map<String, Object> dailyAttendanceList(DailyAttendanceFilterDto filterDto);

	Map<String, Object> monthlyDailyAttendanceList(DailyAttendanceFilterDto filterDto);
}
