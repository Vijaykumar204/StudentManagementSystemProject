package com.studentmanagementsystem.api.dao;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.studentmarks.ClassTopperDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentWithPassOrFail;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.TotalResultCountdto;

public interface StudentMarksDao {

	List<StudentWithPassOrFail> getAllComplianceStudentPassOrFail(String quarterAndYear);

	List<StudentMarksDto> getAllStudentMarks(String quarterAndYear);

	List<TotalResultCountdto> getToatalResultCount(String quarterAndYear);

	ClassTopperDto getClassTopper(String quarterAndYear);

}
