package com.studentmanagementsystem.api.dao;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.studentmarks.ComplianceStudentWithPassOrFail;
import com.studentmanagementsystem.api.model.custom.studentmarks.StudentMarksDto;
import com.studentmanagementsystem.api.model.entity.StudentMarks;

public interface StudentMarksDao {

	StudentMarks getStudentModelandquarterAndYear(Long studentId, String quarterAndYear);

	void saveStudentMarks(StudentMarks studentMark);

	List<ComplianceStudentWithPassOrFail> getAllComplianceStudentPassOrFail(String quarterAndYear);

}
