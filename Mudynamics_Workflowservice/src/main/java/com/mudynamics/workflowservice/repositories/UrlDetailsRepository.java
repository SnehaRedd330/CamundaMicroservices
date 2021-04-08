package com.mudynamics.workflowservice.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mudynamics.workflowservice.models.UrlDetails;



@Repository
public interface UrlDetailsRepository extends JpaRepository<UrlDetails, Long>{

	public UrlDetails findBycomponent(String component);
	public boolean existsByComponent(String component);
	@Transactional
	public String deleteBycomponent(String component);
	
}
