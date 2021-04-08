package com.mudynamics.workflowservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mudynamics.workflowservice.DynamicKafkaConsumer;
import com.mudynamics.workflowservice.models.CreateKafkaEventMappingDTO;
import com.mudynamics.workflowservice.models.KafkaEventMapping;
import com.mudynamics.workflowservice.models.TopicDetails;
import com.mudynamics.workflowservice.models.UpdateKafkaEventMappingDTO;
import com.mudynamics.workflowservice.repositories.TopicDetailsRepository;
import com.mudynamics.workflowservice.services.EventOrchestrationService;



@RestController
public class KafkaTopicEventMappingControllerImpl implements KafkaTopicEventMappingController {
	
	@Autowired
	DynamicKafkaConsumer dynamicKafkaConsumer;
	
	@Autowired
	private EventOrchestrationService eventOrchestrationService;
	
	@Autowired
	private TopicDetailsRepository topicDetailsRepository;
	
	@Override
	public ResponseEntity<List<KafkaEventMapping>> getKafkaTopicProcessMapping() {
		
		return new ResponseEntity<>(eventOrchestrationService.getAllMapping(),HttpStatus.OK);
	}

	@Override
	public ResponseEntity<CreateKafkaEventMappingDTO> createKafkaTopicProcessMapping(@RequestBody CreateKafkaEventMappingDTO eventMappingDTO) {
		
		CreateKafkaEventMappingDTO mapping=eventOrchestrationService.createMapping(eventMappingDTO);
		TopicDetails details=topicDetailsRepository.findByTopicName(eventMappingDTO.getTopics());
		details.setFlag(Boolean.FALSE);
		topicDetailsRepository.save(details);
		return new ResponseEntity<>(mapping,HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<UpdateKafkaEventMappingDTO> updateKafkaTopicProcessMapping(@RequestBody UpdateKafkaEventMappingDTO eventMappingDTO) {
		return new ResponseEntity<>(eventOrchestrationService.updateMapping(eventMappingDTO),HttpStatus.OK);
	}

	@Override
	public void deleteMappingById(@PathVariable long id) {
		eventOrchestrationService.deleteMappingById(id);
	}
	


}
