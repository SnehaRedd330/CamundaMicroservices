package com.mudynamics.workflowservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProcessInstanceDetailsDTO 
{
	@JsonProperty("id")
	  public String id;
	
	@JsonProperty("startTime")
	public String startTime;
	
	@JsonProperty("endTime")
	  public String  endTime;
	
	@JsonProperty("state")
	  public String state;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	
	
}
