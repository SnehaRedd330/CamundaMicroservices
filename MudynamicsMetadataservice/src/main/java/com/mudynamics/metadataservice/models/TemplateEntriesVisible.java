package com.mudynamics.metadataservice.models;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * TemplateEntriesVisible
 * @author SB00485980
 *
 */


@Embeddable
@JsonInclude(Include.NON_NULL)
public class TemplateEntriesVisible implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String assig;
	private String allEntries;
	private boolean executionListeners;
	
	
	
	public boolean isExecutionListeners() {
		return executionListeners;
	}
	public void setExecutionListeners(boolean executionListeners) {
		this.executionListeners = executionListeners;
	}
	public String getAssignee() {
		return assig;
	}
	public void setAssignee(String assig) {
		this.assig = assig;
	}
	public String getAll() {
		return allEntries;
	}
	public void setAll(String allEntries) {
		this.allEntries = allEntries;
	}
	public TemplateEntriesVisible(String assig, String allEntries) {
		super();
		this.assig = assig;
		this.allEntries = allEntries;
	}
	public TemplateEntriesVisible() {
		super();
	}
	
	
}
