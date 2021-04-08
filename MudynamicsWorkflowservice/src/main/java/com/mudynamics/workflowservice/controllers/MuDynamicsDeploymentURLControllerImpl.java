package com.mudynamics.workflowservice.controllers;


import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mudynamics.workflowservice.dto.UrlDetailsDTO;
import com.mudynamics.workflowservice.services.UrlDetailsService;
@RestController
public class MuDynamicsDeploymentURLControllerImpl implements MuDynamicsDeploymentURLController{
	/**
	 * Rest Service method details 
	 * @author SJ00495527
	 *
	 */
	private static Logger LOGGER = Logger.getLogger(MuDynamicsDeploymentURLControllerImpl.class.getName());

	private UrlDetailsService urlService;
	
	@Autowired
	public void setUrlService(UrlDetailsService urlService) {
		this.urlService = urlService;
	}

	

	@Override
	public ResponseEntity<UrlDetailsDTO> createUrl(
			@Valid @RequestBody UrlDetailsDTO urldtl){
		LOGGER.log(Level.INFO,"creating new component request for component name: {0} in database started.....",urldtl.getComponent());
		
		return new ResponseEntity<>(urlService.createUrl(urldtl),HttpStatus.CREATED);
	}


	@Override
	public ResponseEntity<UrlDetailsDTO> getUrl(){
		return new ResponseEntity<>(urlService.getComponentDetails(),HttpStatus.OK);
	}


	@Override
	public void deleteUrl(@PathVariable String componentname){
		LOGGER.log(Level.INFO,"delete request for component {0} environment started",componentname);
		
		urlService.deleteComponentDetails(componentname);
	}


	@Override
	public ResponseEntity<UrlDetailsDTO> updateUrl(
			@Valid @RequestBody UrlDetailsDTO urldtl){
		LOGGER.log(Level.INFO,"updating request for component creation initiated..");
		return new ResponseEntity<>(urlService.updateComponentUrls(urldtl),HttpStatus.OK);
	}
	}


