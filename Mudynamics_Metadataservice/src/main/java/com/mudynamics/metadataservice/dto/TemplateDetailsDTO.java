package com.mudynamics.metadataservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mudynamics.metadataservice.models.TemplateConnectorsScopes;
import com.mudynamics.metadataservice.models.TemplateEntriesVisible;
@JsonInclude(Include.NON_NULL)
public class TemplateDetailsDTO {
	@JsonIgnore
	private long tempId;
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("objName")
	private String objName;
	
	@JsonProperty("appliesTo")
	private List<String> appliesTo;
	
	@JsonProperty("properties")
	private List<TemplatePropertiesDTO> properties;
	
	@JsonProperty("entriesVisible")
	private TemplateEntriesVisible entriesVisible;
	
	@JsonProperty("scopes")
	private TemplateConnectorsScopes scopes;
	
	
	@JsonProperty("scopes")
	@JsonInclude(Include.NON_EMPTY)
	public TemplateConnectorsScopes getScopes() {
		return scopes;
	}
	@JsonProperty("scopes")
	public void setScopes(TemplateConnectorsScopes scopes) {
		this.scopes = scopes;
	}
	@JsonProperty("id")
	public String getid() {
		return id;
	}
	@JsonProperty("id")
	public void setid(String id) {
		this.id = id;
	}
	@JsonProperty("name")
	public String getName() {
		return name;
	}
	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}
	@JsonProperty("objName")
	public String getObjName() {
		return objName;
	}
	@JsonProperty("objName")
	public void setObjName(String objName) {
		this.objName = objName;
	}
	@JsonProperty("appliesTo")
	public List<String> getAppliesTo() {
		return appliesTo;
	}
	@JsonProperty("appliesTo")
	public void setAppliesTo(List<String> appliesTo) {
		this.appliesTo = appliesTo;
	}
	@JsonProperty("properties")
	public List<TemplatePropertiesDTO> getProperties() {
		return properties;
	}
	@JsonProperty("properties")
	public void setProperties(List<TemplatePropertiesDTO> properties) {
		this.properties = properties;
	}
	@JsonProperty("entriesVisible")
	public TemplateEntriesVisible getEntriesVisible() {
		return entriesVisible;
	}
	@JsonProperty("entriesVisible")
	public void setEntriesVisible(TemplateEntriesVisible entriesVisible) {
		this.entriesVisible = entriesVisible;
	}
	
	@JsonIgnore
	public long getTempId() {
		return tempId;
	}
	@JsonIgnore
	public void setTempId(long tempId) {
		this.tempId = tempId;
	} 
}
