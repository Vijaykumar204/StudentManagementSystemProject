package com.studentmanagementsystem.api.serviceimpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.studentmanagementsystem.api.dao.DailyAttendanceDao;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceFilterDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.MonthlyAbsenceDto;
import com.studentmanagementsystem.api.service.ExcelService;
import com.studentmanagementsystem.api.util.WebServiceUtil;

import jakarta.servlet.http.HttpServletResponse;
@Service
public class ExcelServiceImpl implements ExcelService {
	
	@Autowired
	private DailyAttendanceDao dailyAttendanceDao;



/*
 * Download the monthly attendance report
 */

	@Override
	public void downloadMonthlyAttendanceReport(DailyAttendanceFilterDto dailyAttendanceFilterDto,HttpServletResponse response) {
		
		List<MonthlyAbsenceDto> attendanceList =dailyAttendanceDao.monthlyAttendanceList(dailyAttendanceFilterDto);
		
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("monthlyAttendanceReport.xls");
		Row header = sheet.createRow(0);

		XSSFFont font = (XSSFFont) workbook.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setFontName("Arial");
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setBold(true);
		font.setItalic(false);

		CellStyle style = workbook.createCellStyle();
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		
//		CellStyle wrapStyle = workbook.createCellStyle();
//		wrapStyle.setFont(font);
//		wrapStyle.setAlignment(HorizontalAlignment.CENTER);
//		wrapStyle.setWrapText(true);

		Cell sNoCell = header.createCell(0);
		sNoCell.setCellValue("S.No");
		sNoCell.setCellStyle(style);
		
		Cell nameCell = header.createCell(1);
		nameCell.setCellValue("Name");
		nameCell.setCellStyle(style);
		
		Cell classOfStudyCell = header.createCell(2);
		classOfStudyCell.setCellValue("Class of study");
		classOfStudyCell.setCellStyle(style);
		
		Cell dateOfBirthCell = header.createCell(3);
		dateOfBirthCell.setCellValue("DOB");
		dateOfBirthCell.setCellStyle(style);
		
		Cell emailCell = header.createCell(4);
		emailCell.setCellValue("Email");
		emailCell.setCellStyle(style);
		
		Cell phoneNumberCell = header.createCell(5);
		phoneNumberCell.setCellValue("Phone Number");
		phoneNumberCell.setCellStyle(style);
		
		
		Cell totalWorkingDaysCell = header.createCell(6);
		totalWorkingDaysCell.setCellValue("Total Working Days");
		totalWorkingDaysCell.setCellStyle(style);
		
		Cell attendanceCountCell = header.createCell(7);
		if(WebServiceUtil.PRESENT.equals(dailyAttendanceFilterDto.getStatus()))
			attendanceCountCell.setCellValue("Present Count");
		else if(WebServiceUtil.ABSENT.equals(dailyAttendanceFilterDto.getStatus()))
			attendanceCountCell.setCellValue("Absent Count");
		else
			attendanceCountCell.setCellValue("Attendance Count");
		attendanceCountCell.setCellStyle(style);
		
		Cell dateCell = header.createCell(8);
		dateCell.setCellValue("Dates");
		dateCell.setCellStyle(style);
		
		int sno =1;
		for(MonthlyAbsenceDto absent : attendanceList) {
			absent.setSno(sno);
			Row row = sheet.createRow(sno++);
			row.createCell(0).setCellValue(absent.getSno());
			row.createCell(1).setCellValue(absent.getName());
			row.createCell(2).setCellValue(absent.getClassOfStudy());
			row.createCell(3).setCellValue(absent.getDateOfBirth().toString());
			row.createCell(4).setCellValue(absent.getEmail());
			row.createCell(5).setCellValue(absent.getPhoneNumber());
			row.createCell(6).setCellValue(absent.getTotalWorkingDays());
			row.createCell(7).setCellValue(absent.getAttendanceCount());
			row.createCell(8).setCellValue(absent.getAttendanceDate().toString());	
			
		}
		response.setContentType("application/xlsx");
		response.setHeader("Content-Disposition", "attachment; filename = monthlyAttendanceReport.xlsx");

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			workbook.write(bos);
			bos.close();
			byte[] bytes = bos.toByteArray();
			response.getOutputStream().write(bytes);
			response.getOutputStream().flush();
			workbook.close();

		} catch (IOException e) {
			e.printStackTrace();
			
		}	
		
	}
}



