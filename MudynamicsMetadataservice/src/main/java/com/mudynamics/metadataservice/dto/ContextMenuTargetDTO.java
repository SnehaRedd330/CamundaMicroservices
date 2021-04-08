package com.mudynamics.metadataservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ContextMenuTargetDTO {
	
	@JsonProperty("label")
	private String label;
	@JsonProperty("type")
	private String type;
	@JsonProperty("clazz")
	private String clazz;
	
}
