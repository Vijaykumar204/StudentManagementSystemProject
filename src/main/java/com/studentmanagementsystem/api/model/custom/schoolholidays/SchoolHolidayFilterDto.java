package com.studentmanagementsystem.api.model.custom.schoolholidays;

public class SchoolHolidayFilterDto {
	
	private Integer start=0;
	
	private Integer length =10;
	
	private Boolean isHolidayCancelled;
	
	private Integer month;
	
	private Integer year;
	
	private String searchValue;
	
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

	public Boolean getIsHolidayCancelled() {
		return isHolidayCancelled;
	}

	public void setIsHolidayCancelled(Boolean isHolidayCancelled) {
		this.isHolidayCancelled = isHolidayCancelled;
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

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
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
