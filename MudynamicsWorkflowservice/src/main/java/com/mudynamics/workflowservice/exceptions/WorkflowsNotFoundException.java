package com.mudynamics.workflowservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNPROCESSABLE_ENTITY)
public class WorkflowsNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WorkflowsNotFoundException(String message) {
        super(message);
    }

	public WorkflowsNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
}
