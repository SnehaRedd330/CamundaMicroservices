package com.mudynamics.metadataservice.models;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * TemplateProperties
 * @author SB00485980
 *
 */
@Embeddable
@JsonInclude(Include.NON_NULL)
public class TemplateProperties  implements Serializable{
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String label;
	private String type;
	private String value;
	private boolean editable;
	private String description;
	
	@Embedded
	 @AttributeOverrides({
         @AttributeOverride(name = "type", column =  @Column(name = "tptype")),
	     @AttributeOverride(name="source", column = @Column(name= "binding_source",columnDefinition="LONGTEXT"))
	   
    })
	private TemplatePropertiesBinding binding;

	
	
	

	public TemplateProperties() {
		super();
	}


	




	public boolean isEditable() {
		return editable;
	}







	public void setEditable(boolean editable) {
		this.editable = editable;
	}







	public String getDescription() {
		return description;
	}







	public void setDescription(String description) {
		this.description = description;
	}







	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public TemplatePropertiesBinding getBinding() {
		return binding;
	}


	public void setBinding(TemplatePropertiesBinding binding) {
		this.binding = binding;
	}

	
}
