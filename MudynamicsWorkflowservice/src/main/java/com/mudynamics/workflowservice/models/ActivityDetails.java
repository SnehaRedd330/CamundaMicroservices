package com.mudynamics.workflowservice.models;

public class ActivityDetails 
{
	public String activityId;
	public String activityName;
	public String activityType;
	public String assignee;
	public String calledProcessInstanceId;
	public String calledCaseInstanceId;
	public boolean canceled;
	public boolean completeScope;
	public Integer durationInMillis;
    public String endTime;
    public String executionId;
    public String id;
    public String parentActivityInstanceId;
    public String processDefinitionId;
    public String processInstanceId;
    public String startTime;
    public String taskId;
    public String tenantId;
    
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
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
	public String getCalledProcessInstanceId() {
		return calledProcessInstanceId;
	}
	public void setCalledProcessInstanceId(String calledProcessInstanceId) {
		this.calledProcessInstanceId = calledProcessInstanceId;
	}
	public String getCalledCaseInstanceId() {
		return calledCaseInstanceId;
	}
	public void setCalledCaseInstanceId(String calledCaseInstanceId) {
		this.calledCaseInstanceId = calledCaseInstanceId;
	}
	public boolean isCanceled() {
		return canceled;
	}
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
	public boolean isCompleteScope() {
		return completeScope;
	}
	public void setCompleteScope(boolean completeScope) {
		this.completeScope = completeScope;
	}
	public Integer getDurationInMillis() {
		return durationInMillis;
	}
	public void setDurationInMillis(Integer durationInMillis) {
		this.durationInMillis = durationInMillis;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentActivityInstanceId() {
		return parentActivityInstanceId;
	}
	public void setParentActivityInstanceId(String parentActivityInstanceId) {
		this.parentActivityInstanceId = parentActivityInstanceId;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
