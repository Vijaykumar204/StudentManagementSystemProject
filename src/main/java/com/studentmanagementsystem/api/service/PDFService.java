package com.studentmanagementsystem.api.service;

import com.studentmanagementsystem.api.model.custom.CommonFilterDto;

import jakarta.servlet.http.HttpServletResponse;

public interface PDFService {

	void downloadMarkSummaryReport(CommonFilterDto filterDto, HttpServletResponse response);

}
