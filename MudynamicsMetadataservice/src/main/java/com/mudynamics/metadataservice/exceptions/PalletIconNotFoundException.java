package com.mudynamics.metadataservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class PalletIconNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6000938226199680234L;


	public PalletIconNotFoundException(String message){
		super(message);
	}
	
	  public PalletIconNotFoundException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
