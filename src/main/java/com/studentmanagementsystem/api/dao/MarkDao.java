package com.studentmanagementsystem.api.dao;

import java.util.Map;
import java.util.List;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.ResultReport;

public interface MarkDao {

	Map<String,Object> listStudentMarks(CommonFilterDto filterDto);

	List<ResultReport> resultSummaryReport(CommonFilterDto filterDto);

}
