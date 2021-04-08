package com.mudynamics.metadataservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoContextMenuFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	  public NoContextMenuFoundException(String message, Throwable cause) {
	        super(message, cause);
	    }
	
	public NoContextMenuFoundException(String message){
		super(message);
	}
	
}
