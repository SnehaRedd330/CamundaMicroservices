package com.mudynamics.metadataservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class TemplateNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public TemplateNotFoundException(String message){
		super(message);
	}
	
	 public TemplateNotFoundException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
