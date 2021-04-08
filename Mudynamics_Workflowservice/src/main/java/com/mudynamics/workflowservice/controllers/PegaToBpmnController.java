package com.mudynamics.workflowservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="PegaToBpmnController")
@RequestMapping(value="/mudynamics/workflowdetail/v1",produces="text/plain")
public interface PegaToBpmnController
{
	@GetMapping("/getBpmnXml")
	@ApiOperation(
			value = "Returns xml of converted camunda bpmn process in response",
			notes = "Returns xml of converted camunda bpmn process in response."
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
			
			})
	public String pegaToBpmn(@RequestParam("inputFile") String inputFile) throws Exception ;
}


