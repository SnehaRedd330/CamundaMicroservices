package com.mudynamics.workflowservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mudynamics.workflowservice.models.KafkaEventMapping;





@Repository
public interface KafkaEventMappingRepository extends JpaRepository<KafkaEventMapping, Long> {
	
	public KafkaEventMapping findByTopics(String topicName);

}