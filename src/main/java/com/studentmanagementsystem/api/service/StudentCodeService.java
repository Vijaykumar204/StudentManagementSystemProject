package com.studentmanagementsystem.api.service;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.Response;
import com.studentmanagementsystem.api.model.custom.studentcode.StudentCodeDto;

public interface StudentCodeService {

	Response addStudentCode(List<StudentCodeDto> studentCodeDto);

	Response loadStudentCode(String groupCode);
}
