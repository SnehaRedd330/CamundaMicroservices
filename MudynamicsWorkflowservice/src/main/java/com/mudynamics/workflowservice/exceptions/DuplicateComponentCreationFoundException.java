package com.mudynamics.workflowservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
public class DuplicateComponentCreationFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateComponentCreationFoundException(String msg) {
		super(msg);
	}
	
	public DuplicateComponentCreationFoundException(String msg, Throwable t) {
		super(msg, t);
	}
}
