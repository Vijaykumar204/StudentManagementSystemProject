package com.studentmanagementsystem.api.dao;

import java.util.Map;
import java.util.List;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.MarkFilterDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.ResultReport;

public interface MarkDao {

	Map<String,Object> listStudentMarks(MarkFilterDto filterDto);

	List<ResultReport> resultSummaryReport(MarkFilterDto filterDto);

}
