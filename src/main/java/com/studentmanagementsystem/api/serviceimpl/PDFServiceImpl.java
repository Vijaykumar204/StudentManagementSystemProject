package com.studentmanagementsystem.api.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanagementsystem.api.dao.MarkDao;
import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.MarkFilterDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.ResultReport;
import com.studentmanagementsystem.api.service.PDFService;
import com.studentmanagementsystem.api.util.WebServiceUtil;

import jakarta.servlet.http.HttpServletResponse;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;
import java.util.List;

@Service
public class PDFServiceImpl implements PDFService {
	
	@Autowired
	private MarkDao markDao;

//	public void downloadMarkSummaryReport(CommonFilterDto filterDto, HttpServletResponse response) {
//		
//		 List<ResultReport> summaryReportList = markDao.resultSummaryReport(filterDto);
//
//		    try {
//		        response.setContentType("application/pdf");
//		        response.setHeader("Content-Disposition", "attachment; filename=markSummaryReport.pdf");
//
//		        PdfWriter writer = new PdfWriter(response.getOutputStream());
//		        PdfDocument pdfDoc = new PdfDocument(writer);
//		        Document document = new Document(pdfDoc);
//
//		        // Set font
//		        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);	
//
//		        
//		        Paragraph title = new Paragraph("Mark Summary Report")
//		                .setFont(font)
//		                .setFontSize(14);
//		        document.add(title);
//		        document.add(new Paragraph("\n"));
//		        
//		        Paragraph introduction = new Paragraph(String.format(WebServiceUtil.PDF_INTRODUCTION,filterDto.getClassOfStudy()))
//		                .setFont(font)
//		                .setFontSize(12);
//		        document.add(introduction);
//		        document.add(new Paragraph("\n"));
//		        
//		        Paragraph overview = new Paragraph(WebServiceUtil.PDF_OVERVIEW)
//		                .setFont(font)
//		                .setFontSize(12);
//		        document.add(overview);
//		        document.add(new Paragraph("\n"));
//		        
//
//		        // Table with 6 columns
//		        float[] columnWidths = {50F, 120F, 100F,100F, 100F, 100F, 150F};
//		        Table table = new Table(columnWidths);
//		        table.setWidth(UnitValue.createPercentValue(100)); // ✅ correct in iText 7
//
//		        // Header row
//		        String[] headers = {
//		            "S.No", "Quarter and Year","Class", "Total Students", "Total Pass",
//		            "Total Fail", "Total Fail Due To Attendance"
//		        };
//
//		        for (String header : headers) {
//		            Cell cell = new Cell()
//		                    .add(new Paragraph(header).setFont(font))
//		                    .setBackgroundColor(ColorConstants.LIGHT_GRAY);
//		            table.addHeaderCell(cell);
//		        }
//
//		        // Data rows
//		        int sno = 1;
//		        for (ResultReport summary : summaryReportList) {
//		            table.addCell(new Cell().add(new Paragraph(String.valueOf(sno++))));
//		            table.addCell(new Cell().add(new Paragraph(summary.getQuarter())));
//		            table.addCell(new Cell().add(new Paragraph(String.valueOf(summary.getClassOfStudy()))));
//		            table.addCell(new Cell().add(new Paragraph(String.valueOf(summary.getTotalCount()))));
//		            table.addCell(new Cell().add(new Paragraph(String.valueOf(summary.getTotalPass()))));
//		            table.addCell(new Cell().add(new Paragraph(String.valueOf(summary.getTotalFail()))));
//		            table.addCell(new Cell().add(new Paragraph(String.valueOf(summary.getFailDueToAttendance()))));
//		        }
//
//		        document.add(table);
//		        
//		        Paragraph teacherNote = new Paragraph(String.format(WebServiceUtil.PDF_TEACHER_NOTE,filterDto.getClassOfStudy()))
//		                .setFont(font)
//		                .setFontSize(12	);
//		        document.add(teacherNote);
//		        document.add(new Paragraph("\n"));
//		        
//		        Paragraph conclusion = new Paragraph(String.format(WebServiceUtil.PDF_CONCLUSION,filterDto.getClassOfStudy()))
//		                .setFont(font)
//		                .setFontSize(12	);
//		        document.add(conclusion);
//		        document.add(new Paragraph("\n"));
//		        
//		        document.close();
//
//		    } catch (IOException e) {
//		        e.printStackTrace();
//		    }
//		
//	}
	
	
	public void downloadMarkSummaryReport(MarkFilterDto filterDto, HttpServletResponse response) {

	    List<ResultReport> summaryReportList = markDao.resultSummaryReport(filterDto);

	    try {
	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment; filename=markSummaryReport.pdf");

	        PdfWriter writer = new PdfWriter(response.getOutputStream());
	        PdfDocument pdfDoc = new PdfDocument(writer);
	        Document document = new Document(pdfDoc);

	        // Set fonts
	        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
	        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);

	        // Container with border
	        Div borderDiv = new Div();
	        borderDiv.setBorder(new SolidBorder(ColorConstants.BLACK, 1));
	        borderDiv.setPadding(10);

	        // Title
	        Paragraph title = new Paragraph("Mark Summary Report")
	                .setFont(boldFont)
	                .setFontSize(16)
	                .setTextAlignment(TextAlignment.CENTER);
	        borderDiv.add(title);
	        borderDiv.add(new Paragraph("\n"));

	        // Introduction
	        Paragraph introduction = new Paragraph(String.format(WebServiceUtil.PDF_INTRODUCTION, filterDto.getClassOfStudy()))
	                .setFont(boldFont)
	                .setFontSize(12);
	        borderDiv.add(introduction);
	        
	        Paragraph introductionContent = new Paragraph(String.format(WebServiceUtil.PDF_INTRODUCTION_CONTENT, filterDto.getClassOfStudy()))
	                .setFontSize(12);
	        borderDiv.add(introductionContent);
	        borderDiv.add(new Paragraph("\n"));

	        // Overview
	        Paragraph overview = new Paragraph(WebServiceUtil.PDF_OVERVIEW)
	                .setFont(boldFont)
	                .setFontSize(12);
	        borderDiv.add(overview);
	       
	        
	        Paragraph overviewContent = new Paragraph(WebServiceUtil.PDF_OVERVIEW_CONTENT)
	                .setFontSize(12);
	        borderDiv.add(overviewContent);
	        borderDiv.add(new Paragraph("\n"));

	        // Table with 7 columns
	        float[] columnWidths = {50F, 120F, 100F, 100F, 100F, 100F, 150F};
	        Table table = new Table(columnWidths);
	        table.setWidth(UnitValue.createPercentValue(100));

	        // Header row
	        String[] headers = {
	                "S.No", "Quarter and Year", "Class", "Total Students", "Total Pass",
	                "Total Fail", "Total Fail Due To Attendance"
	        };

	        for (String header : headers) {
	            Cell cell = new Cell()
	                    .add(new Paragraph(header).setFont(boldFont))
	                    .setBackgroundColor(ColorConstants.LIGHT_GRAY);
	            table.addHeaderCell(cell);
	        }

	        // Data rows
	        int sno = 1;
	        for (ResultReport summary : summaryReportList) {
	            table.addCell(new Cell().add(new Paragraph(String.valueOf(sno++))));
	            table.addCell(new Cell().add(new Paragraph(summary.getQuarter())));
	            table.addCell(new Cell().add(new Paragraph(String.valueOf(summary.getClassOfStudy()))));
	            table.addCell(new Cell().add(new Paragraph(String.valueOf(summary.getTotalCount()))));
	            table.addCell(new Cell().add(new Paragraph(String.valueOf(summary.getTotalPass()))));
	            table.addCell(new Cell().add(new Paragraph(String.valueOf(summary.getTotalFail()))));
	            table.addCell(new Cell().add(new Paragraph(String.valueOf(summary.getFailDueToAttendance()))));
	        }

	        borderDiv.add(table);
	        borderDiv.add(new Paragraph("\n"));

	        // Teacher note
	        Paragraph teacherNote = new Paragraph(WebServiceUtil.PDF_TEACHER_NOTE)
	                .setFont(boldFont)
	                .setFontSize(12);
	        borderDiv.add(teacherNote);
	        
	        Paragraph teacherNoteContent = new Paragraph(String.format(WebServiceUtil.PDF_TEACHER_NOTE_CONTENT, filterDto.getClassOfStudy()))
	                .setFontSize(12);
	        borderDiv.add(teacherNoteContent);
	        borderDiv.add(new Paragraph("\n"));


	        // Conclusion
	        Paragraph conclusion = new Paragraph(WebServiceUtil.PDF_CONCLUSION)
	                .setFont(boldFont)
	                .setFontSize(12);
	        borderDiv.add(conclusion);
	        
	        
	        Paragraph conclusionContent = new Paragraph(String.format(WebServiceUtil.PDF_CONCLUSION_CONTENT, filterDto.getClassOfStudy()))
	                .setFontSize(12);
	        borderDiv.add(conclusionContent);
	        borderDiv.add(new Paragraph("\n"));

	        // Add borderDiv to document
	        document.add(borderDiv);

	        // Footer
	        Paragraph footer = new Paragraph(
	                String.format("Generated on: %s | Class: %d", 
	                        java.time.LocalDate.now().toString(), filterDto.getClassOfStudy()))
	                .setFont(font)
	                .setFontSize(10)
	                .setTextAlignment(TextAlignment.RIGHT);
	        document.add(footer);

	        document.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


}
