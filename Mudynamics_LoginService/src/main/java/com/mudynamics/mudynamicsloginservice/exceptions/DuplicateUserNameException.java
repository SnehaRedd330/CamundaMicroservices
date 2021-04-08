package com.mudynamics.mudynamicsloginservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateUserNameException extends Exception{

	 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public DuplicateUserNameException(String message) {
	        super(message);
	    }

	    public DuplicateUserNameException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
