package com.studentmanagementsystem.api.model.custom.studentcode;



public class StudentCodeDto {

	
	
	private String code;

	private String groupCode;
	
	private String subGroupCode;
	
	private String description;


	private Boolean isActiveFlag = true;
	
	private Long teacherId;


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
	
	


	public Long getTeacherId() {
		return teacherId;
	}


	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}


	public StudentCodeDto() {
		
	}


	public StudentCodeDto(String code, String groupCode, String subGroupCode, String description) {
		
		this.code = code;
		this.groupCode = groupCode;
		this.subGroupCode = subGroupCode;
		this.description = description;
	}
	
	
	
}
