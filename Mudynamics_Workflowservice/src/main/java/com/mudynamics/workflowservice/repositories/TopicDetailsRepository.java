package com.mudynamics.workflowservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mudynamics.workflowservice.models.TopicDetails;



@Repository
public interface TopicDetailsRepository extends JpaRepository<TopicDetails, Long> {

	public boolean existsByTopicName(String name);
	
	public TopicDetails findByTopicName(String topicName);
}
