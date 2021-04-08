package com.mudynamics.metadataservice.models;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * TemplatePropertiesBinding
 * @author SJ00495527
 *
 */

@JsonInclude(Include.NON_NULL)
@Embeddable
public class TemplatePropertiesBinding implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String source;
	private String type;
	private String name;
	private String target;
	private String variables;
	private String scriptFormat;
	
	
	
	public String getSource() {
		return source;
	}



	public void setSource(String source) {
		this.source = source;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getTarget() {
		return target;
	}



	public void setTarget(String target) {
		this.target = target;
	}



	public String getVariables() {
		return variables;
	}



	public void setVariables(String variables) {
		this.variables = variables;
	}



	public String getScriptFormat() {
		return scriptFormat;
	}



	public void setScriptFormat(String scriptFormat) {
		this.scriptFormat = scriptFormat;
	}



	public TemplatePropertiesBinding() {
		super();
	}

	
}
