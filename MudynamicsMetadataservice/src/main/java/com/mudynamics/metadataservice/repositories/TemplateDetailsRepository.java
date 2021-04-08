package com.mudynamics.metadataservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mudynamics.metadataservice.models.TemplateDetails;
@Repository
public interface TemplateDetailsRepository extends JpaRepository<TemplateDetails, String>{

public void deleteByName(String name);

boolean existsByName(String name);

public List<TemplateDetails> findByPalletIconLabel(String label);
}
