package com.mudynamics.workflowservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
public class DuplicateWorkflowNameWithVersionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public DuplicateWorkflowNameWithVersionException(String msg) {
		super(msg);
	}
	
	public DuplicateWorkflowNameWithVersionException(String msg, Throwable t) {
		super(msg, t);
	}
}
