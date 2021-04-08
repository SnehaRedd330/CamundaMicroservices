package com.mudynamics.metadataservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class DuplicateContextMenuException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1801113154362592367L;

	public DuplicateContextMenuException(String msg) {
		super(msg);
	}
	
	public DuplicateContextMenuException(String msg, Throwable t) {
		super(msg, t);
	}
}
