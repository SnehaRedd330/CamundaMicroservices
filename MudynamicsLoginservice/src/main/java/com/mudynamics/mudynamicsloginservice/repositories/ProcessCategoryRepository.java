package com.mudynamics.mudynamicsloginservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mudynamics.mudynamicsloginservice.model.ProcessCategory;
@Repository
public interface ProcessCategoryRepository extends JpaRepository<ProcessCategory, Long> {
	
	public void deleteByCategory(String category);
	
	public boolean existsByCategory(String category);
	
	public ProcessCategory findByCategory(String category);
}
