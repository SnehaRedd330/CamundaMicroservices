package com.mudynamics.workflowservice.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mudynamics.workflowservice.models.CreateKafkaEventMappingDTO;
import com.mudynamics.workflowservice.models.KafkaEventMapping;
import com.mudynamics.workflowservice.models.UpdateKafkaEventMappingDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="KafkaTopicEventMappingController")
@RequestMapping(value="/mudynamics/workflowdetail/v1",produces="application/json")
public interface KafkaTopicEventMappingController {

	@GetMapping("/getAllMapping")
	@ApiOperation(
			value = "Returns list of Kafka Topics in response",
			notes = "Returns list of Kafka Topics in response.",
			response= KafkaEventMapping.class

	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
			
			})
	public ResponseEntity<List<KafkaEventMapping>> getKafkaTopicProcessMapping();
	
	@PostMapping("/createMapping")
	@ApiOperation(
			value = "Returns list of Kafka Topics in response",
			notes = "Returns list of Kafka Topics in response.",
			response= CreateKafkaEventMappingDTO.class

	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
			
			})
	public ResponseEntity<CreateKafkaEventMappingDTO> createKafkaTopicProcessMapping(@RequestBody CreateKafkaEventMappingDTO eventMappingDTO);


	@PutMapping("/updateMapping")
	@ApiOperation(
			value = "Returns list of Kafka Topics in response",
			notes = "Returns list of Kafka Topics in response.",
			response= UpdateKafkaEventMappingDTO.class

	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
			
			})
	public ResponseEntity<UpdateKafkaEventMappingDTO>  updateKafkaTopicProcessMapping(@RequestBody UpdateKafkaEventMappingDTO updateKafkaEventMappingDTO);

	
	@DeleteMapping("/deleteMapping/{id}")
	@ApiOperation(
			value = "Returns list of Kafka Topics in response",
			notes = "Returns list of Kafka Topics in response."
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
			
			})
	public void deleteMappingById(@PathVariable(value="id",required=true) long id);
}
