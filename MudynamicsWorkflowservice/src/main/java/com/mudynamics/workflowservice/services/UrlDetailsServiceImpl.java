package com.mudynamics.workflowservice.services;

import java.time.Instant;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mudynamics.workflowservice.dto.UrlDetailsDTO;
import com.mudynamics.workflowservice.exceptions.DuplicateComponentCreationFoundException;
import com.mudynamics.workflowservice.exceptions.EntityNotFoundException;
import com.mudynamics.workflowservice.models.UrlDetails;
import com.mudynamics.workflowservice.repositories.UrlDetailsRepository;

@Service
public class UrlDetailsServiceImpl implements UrlDetailsService {
	
	/**
	 * Rest Service method details 
	 * @author SJ00495527
	 *
	 */
	
	@Autowired
	private ModelMapper mapper;
	private UrlDetailsRepository urlRepo;
	
	@Autowired
	public void setUrlRepo(UrlDetailsRepository urlRepo) {
		this.urlRepo = urlRepo;
	}
	


	 
	private static final Logger LOGGER = Logger.getLogger(UrlDetailsServiceImpl.class.getName());	
	
	
	
	@Override
	public UrlDetailsDTO getComponentDetails(){
		UrlDetailsDTO detailsDTO = new UrlDetailsDTO();
		if(urlRepo.existsByComponent("CamundaServerEnvironments")){
			detailsDTO =mapper.map(urlRepo.findAll().get(0), UrlDetailsDTO.class);
		}
		return detailsDTO;
	}

	@Override
	@Transactional
	public void deleteComponentDetails(String componentname){
		
			if(!urlRepo.existsByComponent(componentname))
				throw new EntityNotFoundException("requested component name: "+componentname+" not found in database");
			
			urlRepo.deleteBycomponent(componentname);
			LOGGER.info("camunda server process deployment details for component "+componentname+ "deleted successfully.");
	}

	@Override
	@Transactional
	public UrlDetailsDTO updateComponentUrls(UrlDetailsDTO urldtl){
		UrlDetailsDTO detailsDTO = new UrlDetailsDTO();
		if(urlRepo.existsByComponent(urldtl.getComponent())){
			UrlDetails details=urlRepo.findBycomponent(urldtl.getComponent());
			details.setEnvmt(urldtl.getEnvmt());
			details.setUrl(urldtl.getUrl());
			details.setUsername(urldtl.getUsername());
			details.setPassword(urldtl.getPassword());
			details.setLastModifiedBy(urldtl.getLastModifiedBy());
			details.setLastModifiedDate(Instant.now());
			detailsDTO=mapper.map(urlRepo.save(details), UrlDetailsDTO.class);
			LOGGER.info("CamundaServerEnvironments component updation completed successfully");
		}else 
			detailsDTO=createUrl(urldtl);
		
		return detailsDTO;
	}

	@Override
	public UrlDetailsDTO createUrl(UrlDetailsDTO urldtl)   {
		
		if(urlRepo.existsByComponent(urldtl.getComponent())){
			throw new DuplicateComponentCreationFoundException("component is already in database..");
		}
		UrlDetails details=urlRepo.save(mapper.map(urldtl, UrlDetails.class));
		
		return mapper.map(details, UrlDetailsDTO.class);
	}		
		
	
}
