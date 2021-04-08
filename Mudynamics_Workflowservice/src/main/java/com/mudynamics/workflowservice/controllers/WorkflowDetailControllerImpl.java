package com.mudynamics.workflowservice.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.mudynamics.workflowservice.CamundaRest;
import com.mudynamics.workflowservice.dto.AllDetailsDTO;
import com.mudynamics.workflowservice.dto.CamundaServerRequestDTO;
import com.mudynamics.workflowservice.dto.CamundaServerStatusDTO;
import com.mudynamics.workflowservice.dto.DashboardDataDTO;
import com.mudynamics.workflowservice.exceptions.InvalidInputException;
import com.mudynamics.workflowservice.models.DeploymentDescriptor;
import com.mudynamics.workflowservice.models.ProcessDuplicateCheck;
import com.mudynamics.workflowservice.models.UrlDetails;
import com.mudynamics.workflowservice.models.WorkflowMeatadataDetails;
import com.mudynamics.workflowservice.services.WorkflowDetailService;

@RestController
public class WorkflowDetailControllerImpl implements WorkflowDetailController{
	
	private WorkflowDetailService detailService;

	@Autowired
	public void setDetailService(WorkflowDetailService detailService) {
		this.detailService = detailService;
	}
	
	@Autowired
	private CamundaRest rest;
	
	@Autowired
	private ModelMapper mapper;

	private static final Logger LOGGER = Logger.getLogger(WorkflowDetailControllerImpl.class.getName());

	
	
	@Override
	public ResponseEntity<Map<String,Object>> getAllProcessDefinations(@RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
           @RequestParam String processCategory){
		Map<String,Object> processDetails = new HashMap<String, Object>();
		Map<String,List<AllDetailsDTO>> map= new HashMap<>();
		LOGGER.log(Level.INFO,"featching all workflows from databse started at: {0}",LocalDateTime.now());
		Page<WorkflowMeatadataDetails> meatadataDetails= detailService.getAllWorkflowDetails(pageNo,pageSize,processCategory);
		
		if(meatadataDetails!= null){
		 map =meatadataDetails.getContent().parallelStream()
				 .map(process-> mapper.map(process, AllDetailsDTO.class))
				.sorted(Comparator.comparing(AllDetailsDTO::getVersion).reversed())
		.collect(Collectors.groupingBy(AllDetailsDTO::getProcessDefinitionKey));
		 processDetails.put("workflows", map);
		}
		processDetails.put("TotalWorkflows",meatadataDetails.getTotalElements());
		LOGGER.log(Level.INFO,"featching all workflows from databse completed at: {0}",LocalDateTime.now());
		return new ResponseEntity<>(processDetails,HttpStatus.OK);
	}



	@Override
	public ResponseEntity<Map<String,List<AllDetailsDTO>>> refreshAllProcessDefinations() {
		LOGGER.log(Level.INFO,"refreshing all workflows from databse started at: {0}",LocalDateTime.now());
		Map<String,List<AllDetailsDTO>> workflowDetailsDTOs= new HashMap<>();
		UrlDetails urlDetail=detailService.checkCamundaServerEnvironmentsAvailability();
			workflowDetailsDTOs=detailService.refreshAndGetAllProcess(urlDetail.getUrl());
		LOGGER.log(Level.INFO,"refreshing all workflows completed at: {0} ",LocalDateTime.now());
		return new ResponseEntity<>(workflowDetailsDTOs,HttpStatus.OK);
	}



	@Override
	public ResponseEntity<AllDetailsDTO> getProcessDefinationsByProcessName(@PathVariable String processid,
			@PathVariable long version,@PathVariable String status) {
		return new ResponseEntity<>(detailService.getProcessBynameAndVersion(processid,version,status),HttpStatus.OK);
		
	}



	@Override
	public ResponseEntity<AllDetailsDTO> createNewWorkflow(String userid,
			@Valid @RequestBody AllDetailsDTO detailsDTO) {
		return new ResponseEntity<>(detailService.createNewProcess(userid,detailsDTO),HttpStatus.CREATED);
	}



	@Override
	public ResponseEntity<AllDetailsDTO> saveProcessDefinations(@PathVariable String userid, @Valid @RequestBody AllDetailsDTO allDetailsDTO){
		AllDetailsDTO detailsDTO =new AllDetailsDTO();
		if("deploy-change".equals(allDetailsDTO.getProcessStatus()))
			detailsDTO =detailService.saveDeployedProcessChange(userid, allDetailsDTO);
		else if("saved".equals(allDetailsDTO.getProcessStatus()) || "created".equals(allDetailsDTO.getProcessStatus()))
		detailsDTO =detailService.saveExistingProcess(userid,allDetailsDTO);
		else throw new InvalidInputException("Deployed status process will not be saved...");
		return new ResponseEntity<>(detailsDTO,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<AllDetailsDTO> deployBPMN(@PathVariable (value="userId" ,required=true) String userid,@RequestBody DeploymentDescriptor deploymentDescriptor) throws ParseException,IOException {
		
		JsonObject deployedProcessResponse=rest.postDeployment(deploymentDescriptor);
		LOGGER.log(Level.INFO,"deploying request for workflow name: {0} completed..",deploymentDescriptor.getProcessName());
		AllDetailsDTO allDetailsDTO=detailService.postDeployment(deploymentDescriptor,userid,deployedProcessResponse);
		return new ResponseEntity<>(allDetailsDTO,HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<ProcessDuplicateCheck> checkWorkflowName( @PathVariable String workflowName){
		ProcessDuplicateCheck duplicateCheck =detailService.checkIfNameExists(workflowName);
		return new ResponseEntity<>(duplicateCheck,HttpStatus.OK);
	}


	@Override
	public void deleteSavedWorkflowByProcessDefKeyAndStatus(@PathVariable String processid,@PathVariable String status,@PathVariable long version) {
		detailService.deleteWorkflowByNameAndStatus(processid,status,version);
		
	}



	@Override
	public ResponseEntity<AllDetailsDTO> lockWorkflow(@PathVariable String processid, @PathVariable String userId,@PathVariable String status,@PathVariable long version) {
		
		return new ResponseEntity<>(detailService.lockEditableWorkflow(processid,userId,status,version),HttpStatus.OK);
	}



	@Override
	public ResponseEntity<AllDetailsDTO> unlockWorkflow(@PathVariable String processid, @PathVariable String userId,@PathVariable String status,@PathVariable long version) {
		return new ResponseEntity<>(detailService.unlockWorkflow(processid,userId, status,version),HttpStatus.OK);
	}



	@Override
	public ResponseEntity<CamundaServerStatusDTO> checkCamundaServerConnectivity( @RequestBody @Valid CamundaServerRequestDTO requestDto) {
		LOGGER.log(Level.INFO,"checking camunda server connectivity with given credentials");
		return new ResponseEntity<>(detailService.checkConnectivity(requestDto.getUrl(),requestDto),HttpStatus.OK);

	}



	@Override
	public void deleteByProcessDefId(@PathVariable String processdefinitionid) {
		detailService.deleteProcessDefinitionId(processdefinitionid);
		
	}



	@Override
	public ResponseEntity<JSONObject> getRunningProcessInstances(@PathVariable String processdefinitionid) {
		
		return new ResponseEntity<>(detailService.getRunningProcessInstancesCount(processdefinitionid),HttpStatus.OK);
	}



	@Override
	public ResponseEntity<List<DashboardDataDTO>> getAllDashboardProcessDefinations(@RequestParam String... processCategory) {
		
		return new ResponseEntity<>(detailService.getDashboardData(processCategory),HttpStatus.OK);
	}



	@Override
	public ResponseEntity<Map<String, List<AllDetailsDTO>>> searchAllProcessDefinations(@PathVariable @NotBlank String workflowName,@RequestParam @NotBlank String... processCategory) {
		
		return new ResponseEntity<>(detailService.searchProcessDefinitions(workflowName,processCategory),HttpStatus.OK);
	}


	



	

}
