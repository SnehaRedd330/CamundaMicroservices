package com.mudynamics.workflowservice.models;

public class StatusDetails {
	 public String status;
	   public long count;
	  public String getStatus() {
		return status;
	  }
	 public void setStatus(String status) {
		this.status = status;
	}
	public long  getCount() {
		return count;
	}
	public void setCount(long  count) {
		this.count = count;
	}
	public StatusDetails(String status, long  count) {
		super();
		this.status = status;
		this.count = count;
	}
	   
}
