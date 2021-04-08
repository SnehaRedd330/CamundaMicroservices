package com.mudynamics.workflowservice.models;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ActivityDetailsDTO
{
	@JsonProperty("activityName")
	public String activityName;
	
	@JsonProperty("activityType")
	public String activityType;
	
	@JsonProperty("assignee")
	public String assignee;
	
	@JsonProperty("startTime")
    public String startTime;
	
	@JsonProperty("endTime")
	public String endTime;
	
	@JsonProperty("durationInMillis")
	public  Integer durationInMillis;
	
	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	
	public String getAssignee() {
		return assignee;
	}

	
	public void setAssignee(String assignee) {
		this.assignee = assignee;
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

	public Integer getDurationInMillis() {
		return durationInMillis;
	}

	public void setDurationInMillis(Integer durationInMillis) {
		this.durationInMillis = durationInMillis;
	}

	
    

}
