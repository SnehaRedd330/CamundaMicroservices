package com.mudynamics.mudynamicsloginservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ProcessCategoryDeletionNotAllowedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProcessCategoryDeletionNotAllowedException(String message) {
        super(message);
    }

    public ProcessCategoryDeletionNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
