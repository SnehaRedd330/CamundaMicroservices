package com.mudynamics.mudynamicsloginservice.config;

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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import com.mudynamics.mudynamicsloginservice.exceptions.DuplicateUserNameException;
import com.mudynamics.mudynamicsloginservice.exceptions.RoleDeletionNotAllowedException;
import com.mudynamics.mudynamicsloginservice.payload.ApiError;






@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandlerConfig extends ResponseEntityExceptionHandler{
	
	
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
	    ApiError apiError = new ApiError(
	      HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
	    return new ResponseEntity<>(
	      apiError, new HttpHeaders(), apiError.getStatus());
	}
	 
	
	 
	
	@ExceptionHandler({ RoleDeletionNotAllowedException.class })
	public ResponseEntity<Object> handleRoleDeletionNotAllowedException(RoleDeletionNotAllowedException ex) {
	    ApiError apiError = new ApiError(
	      HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getMessage());
	    return new ResponseEntity<>(
	      apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	@ExceptionHandler({ DuplicateUserNameException.class })
	public ResponseEntity<Object> handleDuplicateUserNameException(DuplicateUserNameException ex) {
	    ApiError apiError = new ApiError(
	      HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getMessage());
	    return new ResponseEntity<>(
	      apiError, new HttpHeaders(), apiError.getStatus());
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
	 
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			  MissingServletRequestParameterException ex, HttpHeaders headers, 
			  HttpStatus status, WebRequest request) {
			    String error = ex.getParameterName() + " parameter is missing";
			     
			    ApiError apiError = 
			      new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
			    return new ResponseEntity<>(
			      apiError, new HttpHeaders(), apiError.getStatus());
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
			    ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));
			 
			    ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, 
			      ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
			    return new ResponseEntity<>(
			      apiError, new HttpHeaders(), apiError.getStatus());
			}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
	  HttpRequestMethodNotSupportedException ex, 
	  HttpHeaders headers, 
	  HttpStatus status, 
	  WebRequest request) {
	    StringBuilder builder = new StringBuilder();
	    builder.append(ex.getMethod());
	    builder.append(
	      " method is not supported for this request. Supported methods are ");
	    ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
	 
	    ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, 
	      ex.getLocalizedMessage(), builder.toString());
	    return new ResponseEntity<>(
	      apiError, new HttpHeaders(), apiError.getStatus());
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
	  protected ResponseEntity<Object> handleNoHandlerFoundException(
			  NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
			    String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
			 
			    ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
			    return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
			}

	
	  
	  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
	  public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			  MethodArgumentTypeMismatchException ex, WebRequest request) {
			    String error = 
			      ex.getName() + " should be of type " + ex.getRequiredType().getName();
			 
			    ApiError apiError = 
			      new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
			    return new ResponseEntity<>(
			      apiError, new HttpHeaders(), apiError.getStatus());
			}
}