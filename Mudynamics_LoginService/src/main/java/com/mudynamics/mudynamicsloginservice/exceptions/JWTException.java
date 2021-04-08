package com.mudynamics.mudynamicsloginservice.exceptions;

public class JWTException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JWTException(String message) {
        super(message);
    }

    public JWTException(String message, Throwable cause) {
        super(message, cause);
    }
}
