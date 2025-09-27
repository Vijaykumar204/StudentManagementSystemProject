package com.studentmanagementsystem.api.service;


import java.util.List;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.MessageResponse;
import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceDto;

public interface DailyAttendanceService {

	Response attendanceList(CommonFilterDto filterDto);

	Response monthlyAttendanceList(CommonFilterDto filterDto);

	MessageResponse saveAttendance(List<DailyAttendanceDto> dailyAttendanceDto);

}
