package com.studentmanagementsystem.api.dao;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.ResultReport;

public interface MarkDao {


	List<StudentMarksDto> getAllStudentMarks(CommonFilterDto filterDto);

	//List<ResultReport> getToatalResultCount(String quarterAndYear);

	//List<ResultReport> getResultReport(CommonFilterDto filterDto);

	List<ResultReport> resultSummaryReport(CommonFilterDto filterDto);



}
