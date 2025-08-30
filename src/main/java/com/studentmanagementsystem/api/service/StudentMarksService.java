package com.studentmanagementsystem.api.service;

import java.util.List;

import org.springframework.http.HttpStatusCode;

import com.studentmanagementsystem.api.model.custom.studentmarks.ClassTopperDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.ComplianceStudentWithPassOrFail;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.TotalResultCountdto;

public interface StudentMarksService {

	

	Object saveStudentMarks(List<StudentMarksDto> studentMarksDto);

	List<ComplianceStudentWithPassOrFail> getAllComplianceStudentPassOrFail(String quarterAndYear);

	List<StudentMarksDto> getAllStudentMarks(String quarterAndYear);

	List<TotalResultCountdto> getToatalResultCount(String quarterAndYear);

	ClassTopperDto getClassTopper(String quarterAndYear);

	



}
