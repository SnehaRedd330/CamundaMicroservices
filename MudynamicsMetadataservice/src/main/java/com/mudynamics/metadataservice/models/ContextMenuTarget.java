package com.mudynamics.metadataservice.models;

import java.io.Serializable;

import javax.persistence.Embeddable;


/*@Entity
@Table(name="ContextMenuTarget")*/
@Embeddable
public class ContextMenuTarget implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private String label;
	private String type;
	private String clazz;
	

	
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
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	
	
	
}
