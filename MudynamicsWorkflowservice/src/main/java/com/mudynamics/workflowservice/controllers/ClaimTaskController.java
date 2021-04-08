package com.mudynamics.workflowservice.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mudynamics.workflowservice.models.ClaimedDetails;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="ClaimTaskDetailsController")
@RequestMapping(value="/mudynamics/workflowdetail/v1",produces="application/json")
public interface ClaimTaskController 
{
	@GetMapping("/getClaimedTasks/{assignee}")
	@ApiOperation(
			value = "Returns list of claimed tasks in response",
			notes = "Returns list of claimed tasks in response."

	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
			
			})
	public List<ClaimedDetails> getClaimedTasks(@PathVariable (value = "assignee" ,required=true) @Size(max = 30, min = 3, message = "Assignee should be between 3 and 30 characters")String assignee) ;
	
	
	
	
	@GetMapping("/getFormvariables/{taskId}")
	@ApiOperation(
			value="Returns form variables in response",
			notes="Returns form variables in response."
			)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
			
			})
	public ResponseEntity<JSONObject> getFormVariables(@PathVariable (value = "taskId" ,required=true) @NotBlank String taskId);

	@PostMapping("/completetask/{taskId}")
	@ApiOperation(
			value="Returns form variables in response",
			notes="Returns form variables in response."
			)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
			
			})
	
	public void postCompleteTask(@PathVariable (value = "taskId" ,required=true) @NotBlank String taskId,@Valid @RequestBody JSONObject payload);

}
