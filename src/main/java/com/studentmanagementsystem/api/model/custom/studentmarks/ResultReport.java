package com.studentmanagementsystem.api.model.custom.studentmarks;

public class ResultReport {
	
	
	private String quarter;
	
	private Integer totalCount;
	
	private Long totalPass;
	
	private Long totalFail;
	
	private Long failDueToMark;
	
	private Long failDueToAttendance;

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Long getTotalPass() {
		return totalPass;
	}

	public void setTotalPass(Long totalPass) {
		this.totalPass = totalPass;
	}

	public Long getTotalFail() {
		return totalFail;
	}

	public void setTotalFail(Long totalFail) {
		this.totalFail = totalFail;
	}

	public Long getFailDueToMark() {
		return failDueToMark;
	}

	public void setFailDueToMark(Long failDueToMark) {
		this.failDueToMark = failDueToMark;
	}

	public Long getFailDueToAttendance() {
		return failDueToAttendance;
	}

	public void setFailDueToAttendance(Long failDueToAttendance) {
		this.failDueToAttendance = failDueToAttendance;
	}

	public ResultReport(String quarter, Integer totalCount, Long totalPass, Long totalFail,
			Long failDueToAttendance) {
	
		this.quarter = quarter;
		this.totalCount = totalCount;
		this.totalPass = totalPass;
		this.totalFail = totalFail;
//		this.failDueToMark = failDueToMark;
		this.failDueToAttendance = failDueToAttendance;
	}

	

	
	

}

