package com.studentmanagementsystem.api.model.custom;

public class Response {
	
	
	   private String status;
	   
	   private Integer draw;
	
	   private Long totalCount ;
	    
	   private Long filterCount;
	   
	
	   private Object data;
	 
  
	
	public Response() {
		
	}
	public Response(String status, Object data) {
		this.status = status;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public Long getFilterCount() {
		return filterCount;
	}
	public void setFilterCount(Long filterCount) {
		this.filterCount = filterCount;
	}
	public Integer getDraw() {
		return draw;
	}
	public void setDraw(Integer draw) {
		this.draw = draw;
	}
	

	
//	@Override
//	public String toString() {
//		return "Response [status=" + status + ", data=" + data + "]";
//	}
}
