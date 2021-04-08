package com.mudynamics.workflowservice.models;

public class DeployedProcessWithStatusCount 
	{
	private String status;
	private long count;
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
		public DeployedProcessWithStatusCount(String status, long  count) {
			
			this.status = status;
			this.count = count;
		}
		   
	}
