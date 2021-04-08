package com.mudynamics.workflowservice.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.mudynamics.workflowservice.models.UrlDetails;
import com.mudynamics.workflowservice.repositories.UrlDetailsRepository;

@RestController
public class CamundaProcessDetailsControllerImpl implements CamundaProcessDetailsController {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private UrlDetailsRepository detailsRepository;
	
	@Value("${camunda.engine}")
	private String camundaEngine;
	
	private static Logger logger = Logger.getLogger(CamundaProcessDetailsControllerImpl.class.getName());
	
	@Override
	public ResponseEntity<List<String>> getCamundaProcessList() {
		List<String> process = new ArrayList<String>();
		 UrlDetails urlDetail=new UrlDetails();
		if(detailsRepository.existsByComponent("CamundaServerEnvironments")){
			urlDetail=detailsRepository.findBycomponent("CamundaServerEnvironments");
		}else throw new RuntimeException("CamundaServerEnvironments details not available to proceed..");
		
		String url =urlDetail.getUrl()+"/"+camundaEngine+"/process-definition";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		HttpEntity<?> entity = new HttpEntity<>(headers);
		try {
		HttpEntity<String> response = restTemplate.exchange(
		      url,
		        HttpMethod.GET, 
		        entity, 
		        String.class);

			JSONArray jsonArray = new JSONArray(response.getBody());
			jsonArray.forEach(item -> {
				 JSONObject jsonObject = (JSONObject) item;
				 String key = jsonObject.getString("key");
				 if(!process.contains(key))
					 process.add(key);
			});
			
		}catch(Exception ex) {
			if(ex instanceof  ResourceAccessException){
				logger.log(Level.SEVERE,"Connection refused to connect..",ex);
				throw new RuntimeException("Connection refused to connect to URL: "+url);
				}
			else throw new RuntimeException("something went wrong to connect");
		}
	return new ResponseEntity<>(process,HttpStatus.OK);
	}

	

}
