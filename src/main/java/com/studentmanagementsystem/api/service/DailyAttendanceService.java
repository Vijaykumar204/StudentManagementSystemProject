package com.studentmanagementsystem.api.service;


import java.util.List;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.MessageResponse;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceFilterDto;

public interface DailyAttendanceService {

	Response dailyAttendanceList(DailyAttendanceFilterDto filterDto);

	Response monthlyDailyAttendanceList(DailyAttendanceFilterDto filterDto);

	MessageResponse saveDailyAttendance(List<DailyAttendanceDto> dailyAttendanceDto);

}
