package com.studentmanagementsystem.api.service;

import java.util.List;

import org.springframework.http.HttpStatusCode;

import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.quarterlyreport.response.ComplianceAndNonComplianceListResponse;
import com.studentmanagementsystem.api.model.custom.studentmarks.ClassTopperDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentWithPassOrFail;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.TotalResultCountdto;
import com.studentmanagementsystem.api.model.custom.studentmarks.response.StudentMarkListResponse;
import com.studentmanagementsystem.api.model.custom.studentmarks.response.StudentWithPassOrFailListResponse;
import com.studentmanagementsystem.api.model.custom.studentmarks.response.TotalResultCountListResponse;

public interface StudentMarksService {

	

	Response saveStudentMarks(List<StudentMarksDto> studentMarksDto);

	StudentWithPassOrFailListResponse getAllComplianceStudentPassOrFail(String quarterAndYear);

	StudentMarkListResponse getAllStudentMarks(String quarterAndYear);

	TotalResultCountListResponse getToatalResultCount(String quarterAndYear);

	Response getClassTopper(String quarterAndYear);

	



}
