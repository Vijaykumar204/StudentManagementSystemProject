package com.studentmanagementsystem.api.dao;

import java.util.Map;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;

public interface HolidayDao {

	Map<String, Object> declaredHolidaysList(CommonFilterDto filterDto);

}
