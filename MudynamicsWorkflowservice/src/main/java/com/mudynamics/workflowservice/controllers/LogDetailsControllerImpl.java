package com.mudynamics.workflowservice.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.mudynamics.workflowservice.models.ActivityDetails;
import com.mudynamics.workflowservice.models.ActivityDetailsDTO;
import com.mudynamics.workflowservice.models.ProcessDetails;
import com.mudynamics.workflowservice.models.ProcessInstanceDetails;
import com.mudynamics.workflowservice.models.ProcessInstanceDetailsDTO;
import com.mudynamics.workflowservice.models.StatusDetails;
import com.mudynamics.workflowservice.services.LogDetailsService;
@RestController
@Validated
public class LogDetailsControllerImpl implements LogDetailsController{
	
	@Autowired
	private LogDetailsService logdetailsservice;

	@Override
	public List<ProcessDetails> getProcessDetsils()  {
		return logdetailsservice.getProcessDetails();
	}
	
	@Override
	public List<ActivityDetailsDTO> getActivityDetails(String procInstId) 
	{
		List<ActivityDetails[]>  activities = logdetailsservice.getActivityDetails(procInstId);
		java.lang.reflect.Type list1 =  new TypeToken<List<ActivityDetailsDTO>>() {}.getType();
		return new ModelMapper().map(activities, list1);
	}

	@Override
	public List<ProcessInstanceDetailsDTO>getProcessInstances(String procDefId) {
		List<ProcessInstanceDetails> instances= logdetailsservice.getProcessInstances(procDefId);
	       java.lang.reflect.Type list =  new TypeToken<List<ProcessInstanceDetailsDTO>>() {}.getType();
	       return new ModelMapper().map(instances, list);
	}

	@Override
	public List<StatusDetails> getProcessStatus() {
		return logdetailsservice.getStatus();		
	}
	
	
}


