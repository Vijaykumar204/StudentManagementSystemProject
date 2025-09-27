package com.studentmanagementsystem.api.dao;

import java.util.Map;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;

public interface DailyAttendanceDao {

	Map<String, Object> attendanceList(CommonFilterDto filterDto);

	Map<String, Object> monthlyAttendanceList(CommonFilterDto filterDto);
}
