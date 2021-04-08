package com.mudynamics.metadataservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TemplatePropertiesDTO {
	@JsonProperty("label")
	private String label;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("value")
	private String value;
	
	@JsonProperty("binding")
	private TemplatePropertiesBindingDTO binding;
	
	@JsonProperty("label")
	public String getLabel() {
		return label;
	}
	
	@JsonProperty("label")
	public void setLabel(String label) {
		this.label = label;
	}
	@JsonProperty("type")
	public String getType() {
		return type;
	}
	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}
	@JsonProperty("value")
	public String getValue() {
		return value;
	}
	@JsonProperty("value")
	public void setValue(String value) {
		this.value = value;
	}
	@JsonProperty("binding")
	public TemplatePropertiesBindingDTO getBinding() {
		return binding;
	}
	@JsonProperty("binding")
	public void setBinding(TemplatePropertiesBindingDTO binding) {
		this.binding = binding;
	}

	@Override
	public String toString() {
		return "TemplatePropertiesDTO [label=" + label + ", type=" + type + ", value=" + value + ", binding=" + binding
				+ "]";
	}
	
	
	
}
