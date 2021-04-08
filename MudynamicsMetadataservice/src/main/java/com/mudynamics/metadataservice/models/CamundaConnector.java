package com.mudynamics.metadataservice.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Embeddable
@JsonInclude(Include.NON_NULL)
public class CamundaConnector implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ElementCollection(fetch = FetchType.LAZY)
    private List<TemplateProperties> properties;

	public List<TemplateProperties> getProperties() {
		return properties;
	}

	public void setProperties(List<TemplateProperties> properties) {
		this.properties = properties;
	}
	
	
}
