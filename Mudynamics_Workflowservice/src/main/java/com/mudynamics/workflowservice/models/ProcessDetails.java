package com.mudynamics.workflowservice.models;

public class ProcessDetails
{
	
	public String processDefinitionId;
	public String name;
	public long version;
	public ProcessDetails(String processDefinitionId, String name, long version, long count, String state) {
		super();
		this.processDefinitionId = processDefinitionId;
		this.name = name;
		this.version = version;
		this.count = count;
		this.state = state;
	}
	public long count;
	public String state;
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	
}
