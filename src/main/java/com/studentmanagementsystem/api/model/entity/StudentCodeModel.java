package com.studentmanagementsystem.api.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "student_code")
public class StudentCodeModel {

	@Id
	@Column(name = "CODE", nullable = false, length = 5)
	private String code;

	@Column(name = "CODE_Description", nullable = false, length = 30)
	private String description;

	@Column(name = "CODE_Group", nullable = false, length = 30)
	private String groupCode;

	@Column(name = "CODE_Sub_Group")
	private String subGroupCode;

	@Column(name = "CODE_Is_Active_Flag", columnDefinition = "BOOLEAN DEFAULT true")
	private Boolean isActiveFlag = true;

	@Column(name = "CODE_Last_Effective_Date")
	private LocalDateTime lastEffectiveDateTime;

	@Column(name = "CODE_Create_User", nullable = false)
	private Long createUser;

	@Column(name = "CODE_Create_Date", nullable = false)
	private LocalDateTime createDate;

	@Column(name = "CODE_Update_User")
	private Long updateUser;

	@Column(name = "CODE_Update_Date")
	private LocalDateTime updatdDate;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getSubGroupCode() {
		return subGroupCode;
	}

	public void setSubGroupCode(String subGroupCode) {
		this.subGroupCode = subGroupCode;
	}

	public Boolean getIsActiveFlag() {
		return isActiveFlag;
	}

	public void setIsActiveFlag(Boolean isActiveFlag) {
		this.isActiveFlag = isActiveFlag;
	}

	public LocalDateTime getLastEffectiveDateTime() {
		return lastEffectiveDateTime;
	}

	public void setLastEffectiveDateTime(LocalDateTime lastEffectiveDateTime) {
		this.lastEffectiveDateTime = lastEffectiveDateTime;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public Long getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}

	public LocalDateTime getUpdatdDate() {
		return updatdDate;
	}

	public void setUpdatdDate(LocalDateTime updatdDate) {
		this.updatdDate = updatdDate;
	}

}
