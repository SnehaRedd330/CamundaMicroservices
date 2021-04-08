package com.mudynamics.workflowservice.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;




@JsonInclude(Include.NON_NULL)
public class ProcessDuplicateCheck {

	private boolean processNameExists;
	
	public boolean isProcessNameExists() {
		return processNameExists;
	}
	public void setProcessNameExists(boolean processNameExists) {
		this.processNameExists = processNameExists;
	}
	
	
}
