package com.studentmanagementsystem.api.service;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceFilterDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;


public interface MarkService {

	Response saveStudentMarks(List<StudentMarksDto> studentMarksDto);

	Response listStudentMarks(QuarterlyAttendanceFilterDto markFilterDto);

//	TotalResultCountListResponse getToatalResultCount(String quarterAndYear);

}
