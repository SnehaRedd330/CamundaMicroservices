package com.mudynamics.workflowservice.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mudynamics.workflowservice.dto.AllDetailsDTO;
import com.mudynamics.workflowservice.dto.CamundaServerRequestDTO;
import com.mudynamics.workflowservice.dto.CamundaServerStatusDTO;
import com.mudynamics.workflowservice.dto.DashboardDataDTO;
import com.mudynamics.workflowservice.models.DeploymentDescriptor;
import com.mudynamics.workflowservice.models.ProcessDuplicateCheck;
import com.mudynamics.workflowservice.models.WorkflowMeatadataDetails;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="WorkflowDetailsController")
@RequestMapping(value="/mudynamics/workflowdetail/v1",produces="application/json")
public interface WorkflowDetailController {

	/**
	 * Rest Service method details 
	 * @author SJ00495527
	 *
	 */
	
	/**
	 * Get All process details from database 
	 * @return Response of all available process details from database. 
	 * @throws InvalidDataException 
	 */
	@GetMapping("/AllWorkflowDetails")
	@ApiOperation(
			value = "Respond all Workflows from db.",
			notes = "Returns list of workflows.",
			response = AllDetailsDTO.class
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					
					})
	public ResponseEntity<Map<String,Object>> getAllProcessDefinations( @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam String processCategory);
	
	/**
	 * Get All process details for dashboard from database 
	 * @return Response of all available process details from database. 
	 * @throws InvalidDataException 
	 */
	@GetMapping("/AllDashboardWorkflowDetails")
	@ApiOperation(
			value = "Respond all Workflows from db.",
			notes = "Returns list of workflows.",
			response = DashboardDataDTO.class
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					
					})
	public ResponseEntity<List<DashboardDataDTO>> getAllDashboardProcessDefinations(@RequestParam String... processCategory);
	
	
	/**
	 * Get All process details matches with search criteria from database 
	 * @return Response of all available process details from database. 
	 * @throws InvalidDataException 
	 */
	@GetMapping("/searchWorkflowDetails/{workflowName}")
	@ApiOperation(
			value = "Respond all Workflows matches with search criteria from db.",
			notes = "Returns list of workflows.",
			response = WorkflowMeatadataDetails.class
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					
					})
	public ResponseEntity<Map<String, List<AllDetailsDTO>>> searchAllProcessDefinations(@PathVariable(value="workflowName", required = true) String workflowName,@RequestParam String... processCategory);
	
	
	/**
	 * Get All process details from database as well as from camunda server
	 * @return Response of all available process details from database. 
	 * @throws InvalidDataException 
	 */
	
	@GetMapping("/refreshDeployedWorkflows")
	@ApiOperation(
			value = "Respond all refreshed workflows from camunda server and db.",
			notes = "Returns list of workflows.",
			response = AllDetailsDTO.class
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					
					})
	public ResponseEntity<Map<String,List<AllDetailsDTO>>> refreshAllProcessDefinations();
	
	
	@GetMapping("/WorkflowDetailsByName/{processid}/{version}/{status}")
	@ApiOperation(
			value = "Respond process Definitation Details",
			notes = "Returns Response ststus.",
			response = AllDetailsDTO.class
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					
					})
	public ResponseEntity<AllDetailsDTO> getProcessDefinationsByProcessName(@PathVariable (value="processid",required=true) String processid,@PathVariable (value="version",required=true) long version,@PathVariable (value="status",required=true) String status);
	
	@PostMapping("/createWorkflow/{userid}")
	@ApiOperation(
			value = "Respond new created workflow Details",
			notes = "Returns Response ststus.",
			response = WorkflowMeatadataDetails.class
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					
					})
	public ResponseEntity<AllDetailsDTO> createNewWorkflow(@PathVariable(value="userid",required=true) String userid , @Valid @RequestBody AllDetailsDTO detailsDTO);

	@PostMapping("/savebpmnxml/{userid}")
			@ApiOperation(
					value = "Respond new created Component and URL Details",
					notes = "Returns Response ststus.",
					response = WorkflowMeatadataDetails.class
			)
			@ApiResponses(
					value = {
							@ApiResponse(code = 404, message = "Service not available"),
							@ApiResponse(code = 500, message = "Unexpected Runtime error")
							
							})
			public ResponseEntity<AllDetailsDTO> saveProcessDefinations(@PathVariable(value="userid" ,required=true) String userid, @Valid @RequestBody AllDetailsDTO allDetailsDTO);
	
	
	@PostMapping("/deploybpmnxml/{userId}")
	@ApiOperation(
			value = "Respond new created Component and URL Details",
			notes = "Returns Response ststus.",
			response = AllDetailsDTO.class
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					
					})
	public ResponseEntity<AllDetailsDTO> deployBPMN(@PathVariable (value="userId" ,required=true) String userid, @Valid @RequestBody DeploymentDescriptor deploymentDescriptor) throws ParseException,ClientProtocolException,IOException;
	
	
	
	@GetMapping("/validateWorkflowName/{workflowName}")
	@ApiOperation(
			value = "Respond duplicate Workflow name Details",
			notes = "Returns duplicate Workflow name ststus.",
			response = String.class
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					
					})
   public ResponseEntity<ProcessDuplicateCheck> checkWorkflowName(@PathVariable (value= "workflowName", required=true) String workflowName);

	@DeleteMapping("/deleteSavedWorkflowDetailsByProcessName/{processid}/{status}/{version}")
	@ApiOperation(
			value = "Respond deleted Workflow  status",
			notes = "Returns deleted Workflow  status."
		
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					
					})
	public void deleteSavedWorkflowByProcessDefKeyAndStatus(@PathVariable (value="processid",required=true) String processid,@PathVariable (value="status",required=true) String status, @PathVariable(value="version") long version);

	
	
	@GetMapping("/lockEditableWorkflow/{processid}/{userId}/{status}/{version}")
	@ApiOperation(
			value = "Respond locked Workflow  Details",
			notes = "Returns locked Workflow.",
			response = AllDetailsDTO.class
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					
					})
	public ResponseEntity<AllDetailsDTO> lockWorkflow(@PathVariable (value= "processid", required=true) String processid,@PathVariable(value="userId",required=true)String userId,@PathVariable(value="status",required=true)String status,@PathVariable(value="version",required=true)long version);

	

	
	@GetMapping("/unlockWorkflow/{processid}/{userId}/{status}/{version}")
	@ApiOperation(
			value = "Respond unlock Workflow status",
			notes = "Returns unlock Workflow details.",
			response = AllDetailsDTO.class
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					
					})
	public ResponseEntity<AllDetailsDTO> unlockWorkflow(@PathVariable (value= "processid", required=true) String processid,@PathVariable(value="userId",required=true)String userId,@PathVariable(value="status",required=true)String status,@PathVariable(value="version",required=true) long version);

	@PostMapping("/checkcamundaserverconnectivity")
	@ApiOperation(
			value = "Respond camunda server connecting status",
			notes = "Returns camunda server connecting status with true or false",
			response = CamundaServerStatusDTO.class
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					
					})
	public ResponseEntity<CamundaServerStatusDTO> checkCamundaServerConnectivity(@RequestBody @Valid CamundaServerRequestDTO requestDTO);

	@DeleteMapping("/deleteByProcessDefinitionId/{processdefinitionid}")
	@ApiOperation(
			value = "Delete given process definition ID from camunda server",
			notes = "Delete given process definition ID from camunda server"
			
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					
					})
	public void deleteByProcessDefId(@PathVariable(value="processdefinitionid") String processdefinitionid);
	
	@GetMapping("/getRunningProcessInstancesByProcessDefId/{processdefinitionid}")
	@ApiOperation(
			value = "Respond List of running process instances",
			notes = "Returns List of running process instances."
			
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					
					})
	public ResponseEntity<JSONObject> getRunningProcessInstances(@PathVariable(value="processdefinitionid") String processdefinitionid);
}
