package com.studentmanagementsystem.api.model.custom.schoolholidays;

import java.time.LocalDate;

public class SchoolHolidaysDto {

	private Long holidayId;
	
	private LocalDate holidayDate;
		
	private String holidayReason;
	
	private Boolean isHolidayCancelled = false;;
	
	private String holidayCancelledReason;

	public Long getHolidayId() {
		return holidayId;
	}

	public void setHolidayId(Long holidayId) {
		this.holidayId = holidayId;
	}

	public LocalDate getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(LocalDate holidayDate) {
		this.holidayDate = holidayDate;
	}

	public String getHolidayReason() {
		return holidayReason;
	}

	public void setHolidayReason(String holidayReason) {
		this.holidayReason = holidayReason;
	}

	public Boolean getIsHolidayCancelled() {
		return isHolidayCancelled;
	}

	public void setIsHolidayCancelled(Boolean isHolidayCancelled) {
		this.isHolidayCancelled = isHolidayCancelled;
	}

	public String getHolidayCancelledReason() {
		return holidayCancelledReason;
	}

	public void setHolidayCancelledReason(String holidayCancelledReason) {
		this.holidayCancelledReason = holidayCancelledReason;
	}

	public SchoolHolidaysDto(Long holidayId, LocalDate holidayDate, String holidayReason, Boolean isHolidayCancelled,
			String holidayCancelledReason) {
	
		this.holidayId = holidayId;
		this.holidayDate = holidayDate;
		this.holidayReason = holidayReason;
		this.isHolidayCancelled = isHolidayCancelled;
		this.holidayCancelledReason = holidayCancelledReason;
	}

	public SchoolHolidaysDto() {
		
	}
	
	
	
	
}
