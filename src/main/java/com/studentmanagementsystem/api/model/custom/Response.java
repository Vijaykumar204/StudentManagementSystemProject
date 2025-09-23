package com.studentmanagementsystem.api.model.custom;

public class Response {
	private String status;
	
	  private Integer totalCount ;
	    
	    private Integer filterCount;
	
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
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getFilterCount() {
		return filterCount;
	}
	public void setFilterCount(Integer filterCount) {
		this.filterCount = filterCount;
	}
	

//	@Override
//	public String toString() {
//		return "Response [status=" + status + ", data=" + data + "]";
//	}
}
