package com.mudynamics.metadataservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DetailsDTO {
	@JsonProperty("label")
	private String label;
	
	@JsonProperty("actionName")
	private String actionName;
	
	@JsonProperty("className")
	private String className;
	
	@JsonProperty("target")
	private ContextMenuTargetDTO target;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public ContextMenuTargetDTO getTarget() {
		return target;
	}

	public void setTarget(ContextMenuTargetDTO target) {
		this.target = target;
	}
	
	
	
}
