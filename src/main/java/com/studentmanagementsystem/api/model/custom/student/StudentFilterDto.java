package com.studentmanagementsystem.api.model.custom.student;

public class StudentFilterDto {
	
	private Integer start =0;
	
	private Integer length =10;
	
	private String searchBy;
	
	private String searchValue;
	
	private String status;
	
	private Integer classOfStudy;
	
	private String residingStatus;
	
	private String sortingBy;
	
	private String sortingOrder;
	
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
	public Integer getClassOfStudy() {
		return classOfStudy;
	}
	public void setClassOfStudy(Integer classOfStudy) {
		this.classOfStudy = classOfStudy;
	}
	public String getResidingStatus() {
		return residingStatus;
	}
	public void setResidingStatus(String residingStatus) {
		this.residingStatus = residingStatus;
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
