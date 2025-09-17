package com.studentmanagementsystem.api.dao;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.quarterlyreport.QuarterlyAttendanceFilterDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.ResultReport;

public interface MarkDao {


	List<StudentMarksDto> getAllStudentMarks(QuarterlyAttendanceFilterDto markFilterDto);

	//List<ResultReport> getToatalResultCount(String quarterAndYear);

	List<ResultReport> getResultReport(QuarterlyAttendanceFilterDto markFilterDto);



}
