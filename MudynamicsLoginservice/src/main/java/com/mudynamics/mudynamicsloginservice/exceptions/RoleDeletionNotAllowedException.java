package com.mudynamics.mudynamicsloginservice.exceptions;

public class RoleDeletionNotAllowedException extends Exception{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RoleDeletionNotAllowedException(String message) {
        super(message);
    }

    public RoleDeletionNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
