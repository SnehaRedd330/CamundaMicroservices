package com.mudynamics.workflowservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mudynamics.workflowservice.services.BPMNGenFromPega;



@RestController
public class PegaToBpmnControllerImpl implements PegaToBpmnController
{

	@Autowired
	private BPMNGenFromPega bpmnService;
	
	@Override
	public String pegaToBpmn(@RequestParam("inputFile") String inputFile) throws Exception  
	{
		// TODO Auto-generated method stub
	return bpmnService.genBpmnFromPega(inputFile);
	}
}
