package com.mudynamics.metadataservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NO_CONTENT)
public class EntityNotFoundException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String msg) {
		super(msg);
	}
	
	public EntityNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}

}