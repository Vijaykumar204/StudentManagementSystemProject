package com.studentmanagementsystem.api.restcontroller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceFilterDto;
import com.studentmanagementsystem.api.service.ExcelService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "report")
public class ExcelController {
	
	@Autowired
	private ExcelService excelService;
	
	/*
	 * Download the monthy attendance report
	 * 
	 * @param dailyAttendanceFilterDto Filter criteria for retrieving monthly attendance.
	 * @author Vijiyakumar
	 */
	
	@PostMapping("attendance/monthly-report")
    public void downloadMonthlyAttendanceReport(@RequestBody DailyAttendanceFilterDto dailyAttendanceFilterDto,HttpServletResponse response) throws IOException {  
    		  excelService.downloadMonthlyAttendanceReport(dailyAttendanceFilterDto,response);

    
    }



}
