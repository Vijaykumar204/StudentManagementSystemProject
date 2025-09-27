package com.studentmanagementsystem.api.service;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.Response;

public interface QuarterlyAttendanceReportService {

	Response quarterlyAttendanceList(CommonFilterDto filterDto);

	void runQuarterlyAttendanceUpdate();

	void findQuarterToUpadte(List<Integer> quarterMonth);

}
