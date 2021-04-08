package com.mudynamics.workflowservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.REQUEST_TIMEOUT)
public class KafkaServerNotReachableException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KafkaServerNotReachableException(String message) {
        super(message);
    }

	public KafkaServerNotReachableException(String msg, Throwable t) {
		super(msg, t);
	}
}
