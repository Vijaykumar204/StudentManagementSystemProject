package com.studentmanagementsystem.api.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "school_holidays")
public class HolidayModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HOL_ID")
	private Long holidayId;
	
	@Column(name="HOL_Date",nullable = false)
	private LocalDate holidayDate;
	
	@Column(name = "HOL_Reason",nullable = false,length=100)
	private String holidayReason;
	
	@Column(name = "Hol_Cancelled", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean isHolidayCancelled = false;
	
	@Column(name = "HOL_Cancel_Reason",length = 100)
	private String holidayCancelledReason;
	
	@ManyToOne
	@JoinColumn(name ="HOL_Create_Teacher" ,referencedColumnName = "TEACH_Id",nullable = false)
	private TeacherModel createTeacher;
	
	@Column(name = "HOL_Create_Date_Time",nullable=false)
	private LocalDateTime createDate;
	
	@ManyToOne
	@JoinColumn(name ="HOL_Update_Teacher" ,referencedColumnName = "TEACH_Id")
	private TeacherModel updateTeacher;
	
	@Column(name = "HOL_Update_Date_Time")
	private LocalDateTime updateDate;

	//Getter and Setter
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
	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public TeacherModel getCreateTeacher() {
		return createTeacher;
	}

	public void setCreateTeacher(TeacherModel createTeacher) {
		this.createTeacher = createTeacher;
	}

	public TeacherModel getUpdateTeacher() {
		return updateTeacher;
	}

	public void setUpdateTeacher(TeacherModel updateTeacher) {
		this.updateTeacher = updateTeacher;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}
	
}


