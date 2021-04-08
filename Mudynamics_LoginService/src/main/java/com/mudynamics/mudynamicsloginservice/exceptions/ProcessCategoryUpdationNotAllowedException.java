package com.mudynamics.mudynamicsloginservice.exceptions;

public class ProcessCategoryUpdationNotAllowedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ProcessCategoryUpdationNotAllowedException(String message) {
        super(message);
    }

    public ProcessCategoryUpdationNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
