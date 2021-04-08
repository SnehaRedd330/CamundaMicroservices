package com.mudynamics.metadataservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TemplatePropertiesBindingDTO {
	@JsonProperty("source")
	private String source;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("target")
	private String target;
	
	@JsonProperty("variables")
	private String variables;
	
	@JsonProperty("source")
	public String getSource() {
		return source;
	}
	@JsonProperty("source")
	public void setSource(String source) {
		this.source = source;
	}
	@JsonProperty("type")
	public String getType() {
		return type;
	}
	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}
	@JsonProperty("name")
	public String getName() {
		return name;
	}
	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}
	@JsonProperty("target")
	public String getTarget() {
		return target;
	}
	@JsonProperty("target")
	public void setTarget(String target) {
		this.target = target;
	}
	@JsonProperty("variables")
	public String getVariables() {
		return variables;
	}
	@JsonProperty("variables")
	public void setVariables(String variables) {
		this.variables = variables;
	}
	@Override
	public String toString() {
		return "TemplatePropertiesBindingDTO [source=" + source + ", type=" + type + ", name=" + name + ", target="
				+ target + ", variables=" + variables + "]";
	}
	
	
}
