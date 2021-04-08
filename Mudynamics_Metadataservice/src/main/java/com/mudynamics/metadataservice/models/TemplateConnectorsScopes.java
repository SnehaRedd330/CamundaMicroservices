package com.mudynamics.metadataservice.models;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemplateConnectorsScopes implements Serializable{
	
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	 
	 @Embedded
	 private CamundaConnector camundaConnector;
	 
	 @JsonProperty("camunda:Connector")
	public CamundaConnector getCamundaConnector() {
		return camundaConnector;
	}
	 @JsonProperty("camunda:Connector")
	public void setCamundaConnector(CamundaConnector camundaConnector) {
		this.camundaConnector = camundaConnector;
	}

	

	
	
	
}
