package com.mudynamics.metadataservice.services;

import java.util.List;

import com.mudynamics.metadataservice.dto.TemplateDetailsDTO;
import com.mudynamics.metadataservice.exceptions.CustomException;
import com.mudynamics.metadataservice.models.TemplateDetails;

public interface TemplateDetailService {

	
	public void deleteTemplateDetailsByName(String palletIconLabel,String templateName);
	List<TemplateDetails> getAllTemplate(String contextMenuLabel);

	List<TemplateDetailsDTO> getAllTemplatesDto()throws CustomException;

	
}
