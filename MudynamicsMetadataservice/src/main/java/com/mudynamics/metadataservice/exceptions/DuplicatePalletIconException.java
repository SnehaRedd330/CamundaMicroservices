package com.mudynamics.metadataservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
public class DuplicatePalletIconException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicatePalletIconException(String msg) {
		super(msg);
	}
	
	public DuplicatePalletIconException(String msg, Throwable t) {
		super(msg, t);
	}
}
