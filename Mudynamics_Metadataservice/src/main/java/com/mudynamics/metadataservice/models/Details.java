package com.mudynamics.metadataservice.models;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;




@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Details  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	 
	private String label;
	private String actionName;
	private String className;
	
	 @Embedded
	 @AttributeOverrides(value = {
		        @AttributeOverride(name = "label", column = @Column(name = "contextMenuTarget_label"))
		    })
	private ContextMenuTarget target;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public ContextMenuTarget getTarget() {
		return target;
	}

	public void setTarget(ContextMenuTarget target) {
		this.target = target;
	}

	

	

	
}
