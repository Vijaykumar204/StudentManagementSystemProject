package com.studentmanagementsystem.api.service;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.Response;

public interface QuarterlyAttendanceService {

	Response quarterlyAttendanceList(CommonFilterDto filterDto);

	void findQuarterToUpadte(List<Integer> quarterMonth);

}
