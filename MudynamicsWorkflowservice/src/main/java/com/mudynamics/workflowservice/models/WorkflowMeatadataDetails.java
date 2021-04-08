package com.mudynamics.workflowservice.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="workflow_Metadata_details")
@JsonInclude(Include.NON_NULL)
public class WorkflowMeatadataDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long identify;
	
	
	@Column(name="Process_name")
	private String processName;

	@Column(name = "process_definition_id", unique=true)
	private String processDefinitionId;
	
	private String processCategory;
	
	private String processDescription;
	
	private String processStatus;
	
	private String processDefinitionKey;
	
	private Boolean visibilityFlag;
	
	private String lockedBy;
	
	private String deploymentId;
	
	private String resource;
	
	private String lockedTimeStamp;
	
	private String lastModifiedBy;
	
	private String lastModifiedTimeStamp;
	
	private String createdBy;
	
	private String deploymentTime;
	
	private long version;
	
	private Boolean suspended;
	private String tenantId;
	private long historyTimeToLive;
	
	@Lob
	 @Column(name="SVGImage")
	private String svgDiagramData;

	 @Lob
	 @Column(name="XMLData")
	private String xmldata;


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

	public String getDeploymentTime() {
			return deploymentTime;
		}

		public void setDeploymentTime(String deploymentTime) {
			this.deploymentTime = deploymentTime;
		}

	public long getIdentify() {
		return identify;
	}


	public void setIdentify(long identify) {
		this.identify = identify;
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



	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}


	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}


	public Boolean getVisibilityFlag() {
		return visibilityFlag;
	}


	public void setVisibilityFlag(Boolean visibilityFlag) {
		this.visibilityFlag = visibilityFlag;
	}


	public String getLockedBy() {
		return lockedBy;
	}


	public void setLockedBy(String lockedBy) {
		this.lockedBy = lockedBy;
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


	public String getLockedTimeStamp() {
		return lockedTimeStamp;
	}


	public void setLockedTimeStamp(String lockedTimeStamp) {
		this.lockedTimeStamp = lockedTimeStamp;
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

	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	
	


	 
}
