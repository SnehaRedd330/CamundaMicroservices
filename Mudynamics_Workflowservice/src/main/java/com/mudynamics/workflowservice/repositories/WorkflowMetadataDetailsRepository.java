package com.mudynamics.workflowservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mudynamics.workflowservice.models.WorkflowMeatadataDetails;

@Repository
public interface WorkflowMetadataDetailsRepository extends JpaRepository< WorkflowMeatadataDetails, Long> 
{
	WorkflowMeatadataDetails findByProcessDefinitionId(String procDefId);
	
	Optional<WorkflowMeatadataDetails> findByProcessDefinitionKeyAndProcessStatus(String defKey,String status);
	
	public void deleteByProcessDefinitionKey(String defKey);
	
	Optional<WorkflowMeatadataDetails> findByProcessDefinitionKeyAndVersion(String defKey,long version);
	
	Optional<WorkflowMeatadataDetails> findByProcessDefinitionKeyAndVersionAndProcessStatus(String defKey,long version, String status);
	
	public boolean existsByProcessDefinitionKeyAndVersionAndProcessStatus(String defKey,long version, String status);
	
	
	@Query("SELECT  max(d.version) from WorkflowMeatadataDetails d where d.processDefinitionKey=?1 And processStatus='deploy-change'")
	public Long getMaxVersionCount(String processDefKey);
	
	@Query("select w from WorkflowMeatadataDetails w where w.processCategory in (?1)")
	public List<WorkflowMeatadataDetails> getAllWorkflows(String... processCategory);
	
	@Query("select w from WorkflowMeatadataDetails w where w.processCategory in (?2) and w.processName like CONCAT('%',(?1),'%')")
	public List<WorkflowMeatadataDetails> searchProcess(String processName,String... processCategory);

	public List<DashboardProcessData> findByProcessCategoryIn(String... processCategory);
	
	public List<WorkflowMeatadataDetails> findByProcessCategoryIn(List<String> names);	
	
	public boolean existsByProcessDefinitionId(String id);
	
	public boolean existsByProcessDefinitionKeyAndProcessStatus(String key,String status);
	
	public WorkflowMeatadataDetails findByProcessDefinitionKey(String key);
	
	public WorkflowMeatadataDetails findByProcessName(String name);
	
	public Optional<List<WorkflowMeatadataDetails>> findAllByProcessStatus(String status);
}
