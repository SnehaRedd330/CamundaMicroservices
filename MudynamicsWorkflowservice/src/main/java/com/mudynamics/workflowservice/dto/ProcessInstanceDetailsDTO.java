package com.mudynamics.workflowservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class ProcessInstanceDetailsDTO {
	@JsonProperty("procInstId")
	  public String procInstId;
	
	@JsonProperty("startTime")
	public String startTime;
	
	@JsonProperty("endTime")
	  public String  endTime;
	
	@JsonProperty("state")
	  public String state;

}