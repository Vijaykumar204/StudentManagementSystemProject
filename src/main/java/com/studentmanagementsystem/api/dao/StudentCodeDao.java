package com.studentmanagementsystem.api.dao;

import java.util.List;

import com.studentmanagementsystem.api.model.custom.studentcode.StudentCodeDto;

public interface StudentCodeDao {

	List<StudentCodeDto> stuCodeList(String groupCode);

}
