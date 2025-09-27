package com.studentmanagementsystem.api.restcontroller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.service.PDFService;
import com.studentmanagementsystem.api.serviceimpl.PDFServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class PDFController {
	
	@Autowired
	private PDFService pdfService;
	
	/*
	 * Download the mark summary report
	 * 
	 * @param filterDto Filter criteria for retrieving report.
	 * @author Vijiyakumar
	 */
	@PostMapping("mark/summary-report")
    public void downloadMarkSummaryReport(@RequestBody CommonFilterDto filterDto,HttpServletResponse response) throws IOException {  
		pdfService.downloadMarkSummaryReport(filterDto,response);
    
    }

}
