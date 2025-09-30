package com.studentmanagementsystem.api.dao;

import java.util.Map;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.student.StudentFilterDto;

public interface StudentDao {

	Map<String, Object> listStudentDetails(StudentFilterDto filterDto);

}
