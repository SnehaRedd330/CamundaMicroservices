package com.mudynamics.workflowservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
public class RunningProcessInstanceAvailableException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RunningProcessInstanceAvailableException(String msg) {
		super(msg);
	}
	
	public RunningProcessInstanceAvailableException(String msg, Throwable t) {
		super(msg, t);
	}
}
