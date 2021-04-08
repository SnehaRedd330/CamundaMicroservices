package com.mudynamics.workflowservice.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllDetailsDTO {

	@JsonProperty("identify")
	private long identify;
	
	@JsonProperty("processName")
	private String processName;
	
	@JsonProperty("processDefinitionId")
	private String processDefinitionId;
	
	@JsonProperty("processCategory")
	private String processCategory;
	
	@JsonProperty("processDescription")
	private String processDescription;
	
	@JsonProperty("processStatus")
	private String processStatus;
	
	@JsonProperty("processDefinitionKey")
	private String processDefinitionKey;
	
	@JsonProperty("visibilityFlag")
	private Boolean visibilityFlag;
	
	@JsonProperty("lockedBy")
	private String lockedBy;
	
	@JsonProperty("deploymentId")
	private String deploymentId;
	
	@JsonProperty("resource")
	private String resource;
	
	@JsonProperty("lockedTimeStamp")
	private String lockedTimeStamp;
	
	@JsonProperty("lastModifiedBy")
	private String lastModifiedBy;
	
	@JsonProperty("lastModifiedTimeStamp")
	private String lastModifiedTimeStamp;
	
	@JsonProperty("createdBy")
	private String createdBy;
	
	@JsonProperty("deploymentTime")
	private String deploymentTime;
	
	@JsonProperty("version")
	@NotNull(message="version can not be null..")
	private long version;
	
	@JsonProperty("suspended")
	private Boolean suspended;
	
	@JsonProperty("tenantId")
	private String tenantId;
	
	@JsonProperty("historyTimeToLive")
	private long historyTimeToLive;
	
	@JsonProperty("svgDiagramData")
	private String svgDiagramData;

	@JsonProperty("xmldata")
	private String xmldata;

	
	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessCategory() {
		return processCategory;
	}

	public void setProcessCategory(String processCategory) {
		this.processCategory = processCategory;
	}

	public String getProcessDescription() {
		return processDescription;
	}

	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public Boolean getVisibilityFlag() {
		return visibilityFlag;
	}

	public void setVisibilityFlag(Boolean visibilityFlag) {
		this.visibilityFlag = visibilityFlag;
	}


	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}


	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public String getLastModifiedTimeStamp() {
		return lastModifiedTimeStamp;
	}

	public void setLastModifiedTimeStamp(String lastModifiedTimeStamp) {
		this.lastModifiedTimeStamp = lastModifiedTimeStamp;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getSvgDiagramData() {
		return svgDiagramData;
	}

	public void setSvgDiagramData(String svgDiagramData) {
		this.svgDiagramData = svgDiagramData;
	}

	public String getXmldata() {
		return xmldata;
	}

	public void setXmldata(String xmldata) {
		this.xmldata = xmldata;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Boolean getSuspended() {
		return suspended;
	}

	public void setSuspended(Boolean suspended) {
		this.suspended = suspended;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	
	public long getHistoryTimeToLive() {
		return historyTimeToLive;
	}

	public void setHistoryTimeToLive(long historyTimeToLive) {
		this.historyTimeToLive = historyTimeToLive;
	}
	
	public long getIdentify() {
		return identify;
	}
	
	public void setIdentify(long identify) {
		this.identify = identify;
	}

	public String getLockedBy() {
		return lockedBy;
	}

	public void setLockedBy(String lockedBy) {
		this.lockedBy = lockedBy;
	}

	public String getLockedTimeStamp() {
		return lockedTimeStamp;
	}

	public void setLockedTimeStamp(String lockedTimeStamp) {
		this.lockedTimeStamp = lockedTimeStamp;
	}

	public String getDeploymentTime() {
		return deploymentTime;
	}

	public void setDeploymentTime(String deploymentTime) {
		this.deploymentTime = deploymentTime;
	}
	
	
	
}
