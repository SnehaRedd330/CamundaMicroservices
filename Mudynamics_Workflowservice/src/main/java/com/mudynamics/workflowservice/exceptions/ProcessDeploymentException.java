package com.mudynamics.workflowservice.exceptions;

public class ProcessDeploymentException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProcessDeploymentException(String message) {
        super(message);
    }

	public ProcessDeploymentException(String msg, Throwable t) {
		super(msg, t);
	}
}
