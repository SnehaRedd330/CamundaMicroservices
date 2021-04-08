package com.mudynamics.workflowservice.services;



import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.mudynamics.workflowservice.Constants.TIME_FORMATTER;
import com.mudynamics.workflowservice.models.CreateKafkaEventMappingDTO;
import com.mudynamics.workflowservice.models.KafkaEventMapping;
import com.mudynamics.workflowservice.models.TopicDetails;
import com.mudynamics.workflowservice.models.UpdateKafkaEventMappingDTO;
import com.mudynamics.workflowservice.repositories.KafkaEventMappingRepository;
import com.mudynamics.workflowservice.repositories.TopicDetailsRepository;



@Service
public class EventOrchestrationService {
	
	@Autowired
	private TopicDetailsRepository topicDetailsRepository;
	
	@Autowired
	private KafkaEventMappingRepository kafkaEventMappingRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	public List<KafkaEventMapping> getAllMapping() {
		List<KafkaEventMapping> eventMappings= kafkaEventMappingRepository.findAll();
		if(!eventMappings.isEmpty() || eventMappings!=null)
		eventMappings.parallelStream().sorted(Comparator.comparing(KafkaEventMapping::getUpdatedAt).reversed());
		
	return eventMappings;
	}

	public CreateKafkaEventMappingDTO createMapping(CreateKafkaEventMappingDTO eventMapping) {
		KafkaEventMapping kafkaEventMapping=new KafkaEventMapping();
		kafkaEventMapping.setCreatedBy(eventMapping.getCreatedBy());
		kafkaEventMapping.setProcessName(eventMapping.getProcessName());
		kafkaEventMapping.setTopics(eventMapping.getTopics());
		kafkaEventMapping.setCreatedAt(new SimpleDateFormat(TIME_FORMATTER).format(new Date()));
		kafkaEventMapping.setUpdatedAt(new SimpleDateFormat(TIME_FORMATTER).format(new Date()));
		KafkaEventMapping mapping= kafkaEventMappingRepository.save(kafkaEventMapping);
		return mapper.map(mapping,CreateKafkaEventMappingDTO.class);
	}

	public UpdateKafkaEventMappingDTO updateMapping(UpdateKafkaEventMappingDTO eventMapping) {
		KafkaEventMapping kafkaEventMapping =mapper.map(eventMapping, KafkaEventMapping.class);
		
		if(kafkaEventMappingRepository.existsById(kafkaEventMapping.getId())) {
			KafkaEventMapping mapping=kafkaEventMappingRepository.findById(kafkaEventMapping.getId()).orElseThrow(()-> new RuntimeException("requested mapping is not available in database."));
			mapping.setProcessName(eventMapping.getProcessName());
			mapping.setTopics(eventMapping.getTopics());
			mapping.setLastModifiedBy(eventMapping.getLastModifiedBy());
			mapping.setUpdatedAt(new SimpleDateFormat(TIME_FORMATTER).format(new Date()));
			kafkaEventMapping=kafkaEventMappingRepository.save(mapping);
		}
		return mapper.map(kafkaEventMapping,UpdateKafkaEventMappingDTO.class);
	}
@Transactional
	public void deleteMappingById(long id) {
	if(kafkaEventMappingRepository.existsById(id)) {
		 KafkaEventMapping eventMapping=kafkaEventMappingRepository.findById(id).get(); 
		 if(topicDetailsRepository.existsByTopicName(eventMapping.getTopics())) {
		 TopicDetails details=topicDetailsRepository.findByTopicName(eventMapping.getTopics());
			details.setFlag(Boolean.TRUE);
		topicDetailsRepository.save(details); }
		
		kafkaEventMappingRepository.deleteById(id);
	}
	else throw new RuntimeException("requested ID not available in database.");
}
	
	public String getWorkflowByTopic(String topicName) {
		KafkaEventMapping eventMapping =kafkaEventMappingRepository.findByTopics(topicName);
		return eventMapping.getProcessName();
	}
}
