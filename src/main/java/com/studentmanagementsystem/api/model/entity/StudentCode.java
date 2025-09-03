package com.studentmanagementsystem.api.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class StudentCode {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "STU_CODE_Id")
	private Long studentCodeId;
	
	@Column(name = "STU_CODE",nullable = false,length=25)
	private String code;
	
	@Column(name = "STU_CODE_Description")
	private String description;
	
	private String groupCode;
	
	private String subGroupCode;
	
	private Boolean isActiveFlag;
	
	private LocalDateTime lastEffectiveDateTime;
	
	private Long createUser;
	
	private LocalDateTime createDate;
	
	private Long updateUser;
	
	private LocalDateTime updatdDate;

}


