package com.mudynamics.workflowservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateKafkaEventMappingDTO {
	
	@JsonProperty("id")
	private long id;
	
	@JsonProperty("topics")
	private String topics;
	
	@JsonProperty("processName")
    private  String processName;
	
	@JsonProperty("lastModifiedBy")
	private  String	lastModifiedBy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTopics() {
		return topics;
	}

	public void setTopics(String topics) {
		this.topics = topics;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
	
}
