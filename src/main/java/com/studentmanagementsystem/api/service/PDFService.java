package com.studentmanagementsystem.api.service;

import com.studentmanagementsystem.api.model.custom.CommonFilterDto;
import com.studentmanagementsystem.api.model.custom.studentmarks.MarkFilterDto;

import jakarta.servlet.http.HttpServletResponse;

public interface PDFService {

	void downloadMarkSummaryReport(MarkFilterDto filterDto, HttpServletResponse response);

}
