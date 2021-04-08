package com.mudynamics.workflowservice.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="CamundaProcessDetailsController")
@RequestMapping(value="/mudynamics/workflowdetail/v1",produces="application/json")
public interface CamundaProcessDetailsController {

	@GetMapping("/getProcessList")
	@ApiOperation(
			value = "Returns list of deployed camunda bpmn process in response",
			notes = "Returns list of deployed camunda bpmn process in response.",
			response = String.class

	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
			
			})
	public ResponseEntity<List<String>> getCamundaProcessList();
}
