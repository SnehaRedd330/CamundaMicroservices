package com.mudynamics.metadataservice.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllMetadataDTO {

	@JsonProperty("label")
	private String label;
	
	@JsonProperty("fileType")
	private String fileType;
	
	@JsonProperty("activity")
	private String activity;
	
	@JsonProperty("image")
	private byte[] image;
	
	@JsonProperty("contextMenus")
	private ContextMenuDTO contextMenus = null;
	
	@JsonProperty("templates")
	private List<TemplateDetailsDTO> templates =null;
	

	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();



	public String getLabel() {
		return label;
	}



	public void setLabel(String label) {
		this.label = label;
	}



	public String getFileType() {
		return fileType;
	}



	public void setFileType(String fileType) {
		this.fileType = fileType;
	}



	public String getActivity() {
		return activity;
	}



	public void setActivity(String activity) {
		this.activity = activity;
	}



	public byte[] getImage() {
		return image;
	}



	public void setImage(byte[] image) {
		this.image = image;
	}



	public ContextMenuDTO getContextMenus() {
		return contextMenus;
	}



	public void setContextMenus(ContextMenuDTO contextMenus) {
		this.contextMenus = contextMenus;
	}



	public List<TemplateDetailsDTO> getTemplates() {
		return templates;
	}



	public void setTemplates(List<TemplateDetailsDTO> templates) {
		this.templates = templates;
	}



	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}



	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}
	
	

	
}
