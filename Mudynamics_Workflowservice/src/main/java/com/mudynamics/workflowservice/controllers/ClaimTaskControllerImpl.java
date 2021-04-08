package com.mudynamics.workflowservice.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.mudynamics.workflowservice.models.ClaimedDetails;
import com.mudynamics.workflowservice.services.ClaimedTaskService;

@RestController
@Validated
public class ClaimTaskControllerImpl implements ClaimTaskController
{

	
	@Autowired
	private ClaimedTaskService claimedTaskService;

	@Override
	public List<ClaimedDetails> getClaimedTasks(
			@Size(max = 30, min = 3, message = "Assignee should be between 3 and 30 characters") String assignee) 
	{
		return claimedTaskService.getClaimedTasks(assignee);
	}

	@Override
	public ResponseEntity<JSONObject> getFormVariables(@NotBlank String taskId) {
		
		 return new ResponseEntity<>(claimedTaskService.getFormVariablesOfClaimedTasks(taskId),HttpStatus.OK);
	}

	@Override
	public void postCompleteTask(@NotBlank String taskId, @Valid JSONObject payload) 
	{
		
	  claimedTaskService.completeTask(taskId, payload);	
	}	
	
}
