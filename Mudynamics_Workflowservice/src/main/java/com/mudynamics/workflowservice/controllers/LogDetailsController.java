package com.mudynamics.workflowservice.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mudynamics.workflowservice.models.ActivityDetailsDTO;
import com.mudynamics.workflowservice.models.ProcessDetails;
import com.mudynamics.workflowservice.models.ProcessInstanceDetailsDTO;
import com.mudynamics.workflowservice.models.StatusDetails;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="LogDetailsController")
@RequestMapping(value="/mudynamics/workflowdetail/v1",produces="application/json")
public interface LogDetailsController 
{
	
	@GetMapping("/processDetails")
	@ApiOperation(
			value = "Returns list of process in response",
			notes = "Returns list of process in response.",
			response=ProcessDetails.class

	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
			
			})
	public List<ProcessDetails> getProcessDetsils() throws MalformedURLException, IOException, Exception;
	
	
	@GetMapping("/ActivityDetails/{procInstId}")
	@ApiOperation(
			value = "Returns list of activities in response",
			notes = "Returns list of activities in response.",
			response=ActivityDetailsDTO.class

	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
			
			})

	List<ActivityDetailsDTO> getActivityDetails(@PathVariable("procInstId")String procInstId);
	
	@GetMapping("/processInstances/{procDefId}")
	@ApiOperation(
			value = "Returns list of processInstances in response",
			notes = "Returns list of processInstances in response.",
			response=ProcessInstanceDetailsDTO.class

	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
			
			})

	public List<ProcessInstanceDetailsDTO> getProcessInstances(@PathVariable ("procDefId")String procDefId);
	
	
	@GetMapping(value ="/getStatus")
	@ApiOperation(
			value = "Returns status of process in response",
			notes = "Returns status of process in response.",
			response=StatusDetails.class
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
			
			})
	
	public List<StatusDetails> getProcessStatus();
}
