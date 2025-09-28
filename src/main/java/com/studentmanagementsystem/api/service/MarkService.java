package com.studentmanagementsystem.api.service;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.Response;

import com.studentmanagementsystem.api.model.custom.studentmarks.markDto;


public interface MarkService {

	Response saveStudentMarks(List<markDto> studentMarksDto);

	Response listStudentMarks(CommonFilterDto filterDto);

	Response resultSummaryReport(CommonFilterDto filterDto);


}
