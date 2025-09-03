package com.studentmanagementsystem.api.dao;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.studentmarks.ClassTopperDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.ComplianceStudentWithPassOrFail;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.TotalResultCountdto;
import com.studentmanagementsystem.api.model.entity.MarkModel;

public interface StudentMarksDao {

	MarkModel getStudentModelandquarterAndYear(Long studentId, String quarterAndYear);

	void saveStudentMarks(MarkModel studentMark);

	List<ComplianceStudentWithPassOrFail> getAllComplianceStudentPassOrFail(String quarterAndYear);

	List<StudentMarksDto> getAllStudentMarks(String quarterAndYear);

	List<TotalResultCountdto> getToatalResultCount(String quarterAndYear);

	ClassTopperDto getClassTopper(String quarterAndYear);



}
