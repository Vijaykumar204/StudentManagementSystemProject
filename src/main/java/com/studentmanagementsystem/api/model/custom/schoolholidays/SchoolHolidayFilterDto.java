package com.studentmanagementsystem.api.model.custom.schoolholidays;

public class SchoolHolidayFilterDto {
	
	private Integer size=0;
	
	private Integer length =10;
	
	private Boolean isHolidayCancelled;
	
	private Integer month;
	
	private Integer year;

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
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
	
	
	
			

}
