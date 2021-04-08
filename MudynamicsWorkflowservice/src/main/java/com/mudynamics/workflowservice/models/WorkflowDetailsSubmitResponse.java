package com.mudynamics.workflowservice.models;



public class WorkflowDetailsSubmitResponse {

	private long id;
	private String name;
	private String deploymentTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDeploymentTime() {
		return deploymentTime;
	}
	public void setDeploymentTime(String deploymentTime) {
		this.deploymentTime = deploymentTime;
	}
	
	
}
