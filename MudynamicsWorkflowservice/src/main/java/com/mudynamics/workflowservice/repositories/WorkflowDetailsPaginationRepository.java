package com.mudynamics.workflowservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.mudynamics.workflowservice.models.WorkflowMeatadataDetails;

@Repository
public interface WorkflowDetailsPaginationRepository extends PagingAndSortingRepository<WorkflowMeatadataDetails,Long>{

	 Page<WorkflowMeatadataDetails> findAllByProcessCategory(Pageable pageable,String processCategory);
	
	
}
