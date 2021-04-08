package com.mudynamics.workflowservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value=HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidInputException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidInputException(String msg) {
		super(msg);
	}
	
	public InvalidInputException(String msg, Throwable t) {
		super(msg, t);
	}
}
