package com.studentmanagementsystem.api.service;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.studentmarks.ComplianceStudentWithPassOrFail;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;

public interface StudentMarksService {

	

	Object saveStudentMarks(List<StudentMarksDto> studentMarksDto);

	List<ComplianceStudentWithPassOrFail> getAllComplianceStudentPassOrFail(String quarterAndYear);



}
