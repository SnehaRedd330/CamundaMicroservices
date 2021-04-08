package com.mudynamics.workflowservice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mudynamics.workflowservice.exceptions.DuplicateWorkflowNameWithVersionException;
import com.mudynamics.workflowservice.exceptions.EntityNotFoundException;
import com.mudynamics.workflowservice.exceptions.InvalidInputException;
import com.mudynamics.workflowservice.exceptions.WorkflowsNotFoundException;





@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandlerConfig extends ResponseEntityExceptionHandler{
	
	
	
	
	 private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
	        return new ResponseEntity<>(apiError, apiError.getStatus());
	    }
	 
	 
	 
	 @ExceptionHandler(EntityNotFoundException.class)
	    protected ResponseEntity<Object> handleEntityNotFoundException(
	    		EntityNotFoundException ex) {
	        ApiError apiError = new ApiError(HttpStatus.NO_CONTENT,ex.getLocalizedMessage(),ex.getMessage());
	        return buildResponseEntity(apiError);
	    }
	 
	 @ExceptionHandler(DuplicateWorkflowNameWithVersionException.class)
	    protected ResponseEntity<Object> handleDuplicateWorkflowNameWithVersionException(
	    		DuplicateWorkflowNameWithVersionException ex) {
	        ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE,ex.getLocalizedMessage(),ex.getMessage());
	        return buildResponseEntity(apiError);
	    }
	 
	 @ExceptionHandler(InvalidInputException.class)
	    protected ResponseEntity<Object> handleInvalidInputException(
	    		InvalidInputException ex) {
	        ApiError apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY,ex.getLocalizedMessage(),ex.getMessage());
	        return buildResponseEntity(apiError);
	    }
	 
	 @ExceptionHandler(WorkflowsNotFoundException.class)
	    protected ResponseEntity<Object> handleWorkflowsNotFoundException(
	    		WorkflowsNotFoundException ex) {
		  ApiError apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY,ex.getLocalizedMessage(),ex.getMessage());
	        return buildResponseEntity(apiError);
	    }
	 
		@ExceptionHandler(Exception.class)
	    protected ResponseEntity<Object> handleGeneralException(
	           Exception ex) {
	        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,ex.getLocalizedMessage(),ex.getMessage());
	        return buildResponseEntity(apiError);
	    }
	 
	 
	@Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(new ApiError(BAD_REQUEST, ex.getLocalizedMessage(),error));
    }

	@Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return buildResponseEntity(new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(),builder.substring(0, builder.length() - 2)));
    }
	
	 @Override
	    protected ResponseEntity<Object>
	    handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	                                 HttpHeaders headers,
	                                 HttpStatus status, WebRequest request) {

	        Map<String, Object> body = new LinkedHashMap<>();
	        body.put("timestamp", new Date());
	        body.put("status", status.value());
	        body.put("error", ex.getMessage());
	        //Get all fields errors
	        List<String> errors = ex.getBindingResult()
	                .getFieldErrors()
	                .stream()
	                .map(x -> x.getDefaultMessage())
	                .collect(Collectors.toList());

	        body.put("messages", errors);

	        return new ResponseEntity<>(body, headers, status);

	    }
	
	 @ExceptionHandler(javax.validation.ConstraintViolationException.class)
		public ResponseEntity<Object> handleConstraintViolation(
				  ConstraintViolationException ex, WebRequest request) {
				    List<String> errors = new ArrayList<>();
				    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
				        errors.add(violation.getRootBeanClass().getName() + " " + 
				          violation.getPropertyPath() + ": " + violation.getMessage());
				    }
				 
				    ApiError apiError = 
				      new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
				    return new ResponseEntity<>(
				      apiError, new HttpHeaders(), apiError.getStatus());
				}
	
	  @Override
	    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	        String error = "Malformed JSON request";
	        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),error));
	    }
	  
	  @Override
	    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	        String error = "Error writing JSON output";
	        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(),error));
	    }
	  
	  @Override
	    protected ResponseEntity<Object> handleNoHandlerFoundException(
	            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	        ApiError apiError = new ApiError(BAD_REQUEST,ex.getLocalizedMessage(), String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
	        return buildResponseEntity(apiError);
	    }

	  @ExceptionHandler(DataIntegrityViolationException.class)
	    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
	                                                                  WebRequest request) {
	        if (ex.getCause() instanceof ConstraintViolationException) {
	            return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, ex.getLocalizedMessage(),"Database error"));
	        }
	        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(),ex.getMessage()));
	    }
	  
	  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
	    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
	                                                                      WebRequest request) {
	        ApiError apiError = new ApiError(BAD_REQUEST,ex.getMessage(),String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
	        return buildResponseEntity(apiError);
	    }
}