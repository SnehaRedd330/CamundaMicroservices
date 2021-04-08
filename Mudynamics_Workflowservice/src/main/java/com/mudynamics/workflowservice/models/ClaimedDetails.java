package com.mudynamics.workflowservice.models;

import java.util.List;

public class ClaimedDetails 
{
   
    private long count;
    private String activityName;
    public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	private  List<ClaimedIds> anId;

	public List<ClaimedIds> getAnId() {
		return anId;
	}
	public void setAnId(List<ClaimedIds> anId) {
		this.anId = anId;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	} 
}
