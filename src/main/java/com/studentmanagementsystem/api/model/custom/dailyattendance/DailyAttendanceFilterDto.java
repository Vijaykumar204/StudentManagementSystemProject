package com.studentmanagementsystem.api.model.custom.dailyattendance;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.studentmanagementsystem.api.util.WebServiceUtil;

public class DailyAttendanceFilterDto {
	
	private Integer start =0;
	
	private Integer length =10;
	
	private String searchBy;
	
	private String searchValue;
	
	private String status;
	
	private String approvedStatus;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern =WebServiceUtil.APP_DATE_FORMAT)
	private LocalDate date;
	
	private Integer classOfStudy;
	
	private Integer month;
	
	private Integer year;
	
	private String operationBy;
	
	private Long operationValue;
	
	private String sortingBy;
	
	private String sortingOrder;
	
	private String monthlyAbsence;


	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getClassOfStudy() {
		return classOfStudy;
	}

	public void setClassOfStudy(Integer classOfStudy) {
		this.classOfStudy = classOfStudy;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getMonthlyAbsence() {
		return monthlyAbsence;
	}

	public void setMonthlyAbsence(String monthlyAbsence) {
		this.monthlyAbsence = monthlyAbsence;
	}

	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApprovedStatus() {
		return approvedStatus;
	}

	public void setApprovedStatus(String approvedStatus) {
		this.approvedStatus = approvedStatus;
	}

	public String getOperationBy() {
		return operationBy;
	}

	public void setOperationBy(String operationBy) {
		this.operationBy = operationBy;
	}




	public Long getOperationValue() {
		return operationValue;
	}

	public void setOperationValue(Long operationValue) {
		this.operationValue = operationValue;
	}

	public String getSortingBy() {
		return sortingBy;
	}

	public void setSortingBy(String sortingBy) {
		this.sortingBy = sortingBy;
	}

	public String getSortingOrder() {
		return sortingOrder;
	}

	public void setSortingOrder(String sortingOrder) {
		this.sortingOrder = sortingOrder;
	}
	
	


	
	
	
}
