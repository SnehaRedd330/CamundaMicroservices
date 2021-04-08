package com.mudynamics.workflowservice.controllers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.errors.TimeoutException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.web.bind.annotation.RestController;

import com.mudynamics.workflowservice.DynamicKafkaConsumer;
import com.mudynamics.workflowservice.exceptions.KafkaServerNotReachableException;
import com.mudynamics.workflowservice.models.TopicDetails;
import com.mudynamics.workflowservice.models.TopicDetailsDTO;
import com.mudynamics.workflowservice.repositories.TopicDetailsRepository;



@RestController
public class KafkaTopicControllerImpl implements KafkaTopicController{

	@Autowired
	private ConsumerFactory<String, String> consumerFactory;
	
	@Autowired
	private TopicDetailsRepository topicDetailsRepository;
	
	@Autowired
	private DynamicKafkaConsumer dynamicKafkaConsumer;
	
	@Autowired
	private ModelMapper mapper;
	@Override
	public ResponseEntity<List<TopicDetailsDTO>> getKafkaTopics() {
		try {
			List<String> finalTopicList=new ArrayList<String>();
			Consumer<String, String> consumer = null;
			consumer=consumerFactory.createConsumer();
			Map<String, List<PartitionInfo>> map = consumer.listTopics(Duration.ofSeconds(1));
			if(map!=null) {
			map.remove("__consumer_offsets");
			Set<String> topiclist= map.keySet();
			
			topiclist.forEach(item -> {
				
					if(item.startsWith("WF")) {
						finalTopicList.add(item);
					}
				if(item.startsWith("WF") && !topicDetailsRepository.existsByTopicName(item)) {
					TopicDetails details = new TopicDetails(item, Boolean.TRUE);
					topicDetailsRepository.save(details);
					dynamicKafkaConsumer.createDynamicConsumer(item);
				}	
			});
			topicDetailsRepository.findAll().forEach(item -> {
				if(!finalTopicList.contains(item.getTopicName())) {
					dynamicKafkaConsumer.stopDynamicsConsumer(item.getTopicName());
						topicDetailsRepository.deleteById(item.getId());}	
				
			});
			}
		}catch(Exception ex) {
			if(ex instanceof TimeoutException) {
				throw new KafkaServerNotReachableException("Kafka server not reachable to connect...");
			}
			else throw new RuntimeException("something went wrong in getkafkatopics...");
		}
		return new ResponseEntity<> (topicDetailsRepository.findAll().stream().map(topic-> mapper.map(topic,TopicDetailsDTO.class)).collect(Collectors.toList()),HttpStatus.OK);
	}

}
