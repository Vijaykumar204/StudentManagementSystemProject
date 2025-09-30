package com.studentmanagementsystem.api.serviceimpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.dao.DailyAttendanceDao;
import com.studentmanagementsystem.api.dao.MarkDao;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.DailyAttendanceFilterDto;
import com.studentmanagementsystem.api.model.custom.dailyattendance.MonthlyAbsenceDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.MarkFilterDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.markDto;
import com.studentmanagementsystem.api.service.ExcelService;
import com.studentmanagementsystem.api.util.WebServiceUtil;

import jakarta.servlet.http.HttpServletResponse;
@Service
public class ExcelServiceImpl implements ExcelService {
	
	@Autowired
	private DailyAttendanceDao dailyAttendanceDao;
	
	@Autowired
	private MarkDao markDao;



/*
 * Download the monthly attendance report
 */
@SuppressWarnings("unchecked")
@Override
public void downloadMonthlyAttendanceReport(DailyAttendanceFilterDto filterDto, HttpServletResponse response) {

	Map<String, Object> result = dailyAttendanceDao.monthlyDailyAttendanceList(filterDto);

	List<MonthlyAbsenceDto> monthlyAttendanceList = (List<MonthlyAbsenceDto>) result.get("data");

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

	Cell sNoCell = header.createCell(0);
	sNoCell.setCellValue("S.No");
	sNoCell.setCellStyle(style);

	Cell nameCell = header.createCell(1);
	nameCell.setCellValue("Name");
	nameCell.setCellStyle(style);

	Cell dateOfBirthCell = header.createCell(3);
	dateOfBirthCell.setCellValue("DOB");
	dateOfBirthCell.setCellStyle(style);

	Cell emailCell = header.createCell(4);
	emailCell.setCellValue("Email");
	emailCell.setCellStyle(style);

	Cell phoneNumberCell = header.createCell(5);
	phoneNumberCell.setCellValue("Phone Number");
	phoneNumberCell.setCellStyle(style);

	Cell classOfStudyCell = header.createCell(2);
	classOfStudyCell.setCellValue("Class of study");
	classOfStudyCell.setCellStyle(style);

	Cell totalWorkingDaysCell = header.createCell(6);
	totalWorkingDaysCell.setCellValue("Total Working Days");
	totalWorkingDaysCell.setCellStyle(style);

	Cell attendanceCountCell = header.createCell(7);
	if (WebServiceUtil.PRESENT.equals(filterDto.getAttendnaceStatus()))
		attendanceCountCell.setCellValue("Present Count");
	else if (WebServiceUtil.ABSENT.equals(filterDto.getAttendnaceStatus()))
		attendanceCountCell.setCellValue("Absent Count");
	else
		attendanceCountCell.setCellValue("Attendance Count");
	attendanceCountCell.setCellStyle(style);

	Cell dateCell = header.createCell(8);
	dateCell.setCellValue("Dates");
	dateCell.setCellStyle(style);

	int sno = 1;
	for (MonthlyAbsenceDto absent : monthlyAttendanceList) {
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

 /*
  * Download the mark detail report
  */
	@SuppressWarnings("unchecked")
	@Override
	public void downloadMarkDetailReport(MarkFilterDto filterDto, HttpServletResponse response) {
		
		Map<String,Object> result = markDao.listStudentMarks(filterDto);
		List<markDto> markList = (List<markDto>) result.get("data");
		
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("markDetailReport.xls");
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
		
		
		Cell quarterCell = header.createCell(6);
		quarterCell.setCellValue("Quarter and Year");
		quarterCell.setCellStyle(style);
		
		Cell tamilCell = header.createCell(7);
		tamilCell.setCellValue("Tamil");
		tamilCell.setCellStyle(style);
		
		Cell englishCell = header.createCell(8);
		englishCell.setCellValue("English");
		englishCell.setCellStyle(style);
		
		Cell mathsCell = header.createCell(9);
		mathsCell.setCellValue("Maths");
		mathsCell.setCellStyle(style);
		
		Cell scienceCell = header.createCell(10);
		scienceCell.setCellValue("Science");
		scienceCell.setCellStyle(style);
		
		Cell socialCell = header.createCell(11);
		socialCell.setCellValue("Social Science");
		socialCell.setCellStyle(style);
		
		Cell totalCell = header.createCell(12);
		totalCell.setCellValue("Total");
		totalCell.setCellStyle(style);
		
		Cell percentageCell = header.createCell(13);
		percentageCell.setCellValue("Percentage");
		percentageCell.setCellStyle(style);
		
		Cell resultCell = header.createCell(14);
		resultCell.setCellValue("Result");
		resultCell.setCellStyle(style);
		
		int sno =1;
		for(markDto mark : markList) {
			mark.setSno(sno);
			Row row = sheet.createRow(sno++);
			row.createCell(0).setCellValue(mark.getSno());
			row.createCell(1).setCellValue(mark.getName());
			row.createCell(2).setCellValue(mark.getClassOfStudy());
			row.createCell(3).setCellValue(mark.getDateOfBirth().toString());
			row.createCell(4).setCellValue(mark.getEmail());
			row.createCell(5).setCellValue(mark.getPhoneNumber());
			row.createCell(7).setCellValue(mark.getTamil());
			row.createCell(8).setCellValue(mark.getEnglish());
			row.createCell(9).setCellValue(mark.getMaths());
			row.createCell(10).setCellValue(mark.getScience());
			row.createCell(11).setCellValue(mark.getSocialScience());
			row.createCell(12).setCellValue(mark.getTotalMarks());
			row.createCell(13).setCellValue(mark.getPercentage()+" %");
			row.createCell(14).setCellValue(mark.getResult());
			
		}
		response.setContentType("application/xlsx");
		response.setHeader("Content-Disposition", "attachment; filename = markDetailReport.xlsx");

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

	 /*
	  * Download the mark summary report
	  */

//		@Override
//		public void downloadMarkSummaryReport(CommonFilterDto filterDto, HttpServletResponse response) {
//			List<ResultReport> summaryReportList = markDao.resultSummaryReport(filterDto);
//
//			Workbook workbook = new XSSFWorkbook();
//			Sheet sheet = workbook.createSheet("markSummaryReport.xls");
//			Row header = sheet.createRow(0);
//
//			XSSFFont font = (XSSFFont) workbook.createFont();
//			font.setFontHeightInPoints((short) 10);
//			font.setFontName("Arial");
//			font.setColor(IndexedColors.BLACK.getIndex());
//			font.setBold(true);
//			font.setItalic(false);
//
//			CellStyle style = workbook.createCellStyle();
//			style.setFont(font);
//			style.setAlignment(HorizontalAlignment.CENTER);
//
//			Cell sNoCell = header.createCell(0);
//			sNoCell.setCellValue("S.No");
//			sNoCell.setCellStyle(style);
//
//			Cell quarterCell = header.createCell(1);
//			quarterCell.setCellValue("Quarter and Year");
//			quarterCell.setCellStyle(style);
//
//			Cell totalCell = header.createCell(2);
//			totalCell.setCellValue("Total Students");
//			totalCell.setCellStyle(style);
//
//			Cell passCell = header.createCell(3);
//			passCell.setCellValue("Total Pass");
//			passCell.setCellStyle(style);
//
//			Cell failCell = header.createCell(4);
//			failCell.setCellValue("Total Fail");
//			failCell.setCellStyle(style);
//
//			Cell dueToAttendanceCell = header.createCell(5);
//			dueToAttendanceCell.setCellValue("Total Fail Due To Attendance");
//			dueToAttendanceCell.setCellStyle(style);
//
//			int rowValue = 1;
//			int sno = 0;
//			for (ResultReport summary : summaryReportList) {
//				sno = rowValue;
//				Row row = sheet.createRow(rowValue++);
//				row.createCell(0).setCellValue(sno);
//				row.createCell(1).setCellValue(summary.getQuarter());
//				row.createCell(2).setCellValue(summary.getTotalCount());
//				row.createCell(3).setCellValue(summary.getTotalPass());
//				row.createCell(4).setCellValue(summary.getTotalFail());
//				row.createCell(5).setCellValue(summary.getFailDueToAttendance());
//
//			}
//			response.setContentType("application/xlsx");
//			response.setHeader("Content-Disposition", "attachment; filename = markSummaryReport.xlsx");
//
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//
//			try {
//				workbook.write(bos);
//				bos.close();
//				byte[] bytes = bos.toByteArray();
//				response.getOutputStream().write(bytes);
//				response.getOutputStream().flush();
//				workbook.close();
//
//			} catch (IOException e) {
//				e.printStackTrace();
//
//			}
//
//		}
}



