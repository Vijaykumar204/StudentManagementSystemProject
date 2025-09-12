package com.studentmanagementsystem.api.service;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.response.StudentMarkListResponse;
import com.studentmanagementsystem.api.model.custom.studentmarks.response.StudentWithPassOrFailListResponse;
import com.studentmanagementsystem.api.model.custom.studentmarks.response.TotalResultCountListResponse;

public interface StudentMarksService {

	Response saveStudentMarks(List<StudentMarksDto> studentMarksDto);

	StudentWithPassOrFailListResponse getAllComplianceStudentPassOrFail(String quarterAndYear);

	StudentMarkListResponse getAllStudentMarks(String quarterAndYear,Boolean resultStatus,int classOfStudy);

	TotalResultCountListResponse getToatalResultCount(String quarterAndYear);

	Response getClassTopper(String quarterAndYear);

}
