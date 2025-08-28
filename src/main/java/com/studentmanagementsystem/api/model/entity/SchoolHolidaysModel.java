package com.studentmanagementsystem.api.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "school_holidays")
public class SchoolHolidaysModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "HOL_ID")
	private Long holidayId;
	
	@Column(name="HOL_Date")
	private LocalDate holidayDate;
	
	@Column(name = "HOL_Reason")
	private String holidayReason;
	
	
	@Column(name = "Hol_Cancelled", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean isHolidayCancelled = false;
	
	
	@Column(name = "HOL_Cancel_Reason")
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
	
}


