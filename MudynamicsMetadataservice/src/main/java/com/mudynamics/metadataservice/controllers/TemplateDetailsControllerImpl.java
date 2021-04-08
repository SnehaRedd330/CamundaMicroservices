package com.mudynamics.metadataservice.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mudynamics.metadataservice.dto.TemplateDetailsDTO;
import com.mudynamics.metadataservice.exceptions.CustomException;
import com.mudynamics.metadataservice.exceptions.PalletIconNotFoundException;
import com.mudynamics.metadataservice.models.TemplateDetails;
import com.mudynamics.metadataservice.repositories.PaletteIconRepository;
import com.mudynamics.metadataservice.repositories.TemplateDetailsRepository;
import com.mudynamics.metadataservice.services.TemplateDetailService;

@RestController
public class TemplateDetailsControllerImpl implements TemplateDetailsController{

	
	private TemplateDetailService tempService;

	@Autowired
	public void setTempService(TemplateDetailService tempService) {
		this.tempService = tempService;
	}
	@Autowired
	private PaletteIconRepository iconRepo;
	
	@Autowired
	private TemplateDetailsRepository tempRepo;
	@Autowired
	private ModelMapper mapper;
	
	private static Logger logger = Logger.getLogger(TemplateDetailsControllerImpl.class.getName());
	
	

	@Override
	@Transactional
	public ResponseEntity<String> deleteTemplate(@PathVariable String palletIconLabel,@PathVariable String templateName) {
		logger.log(Level.INFO,"deletion request for template details  Name: {0} started",templateName);
		if(!iconRepo.existsByLabel(palletIconLabel))
			throw new PalletIconNotFoundException("Pallet Icon LABEL: "+palletIconLabel+" not found in database.");
		tempRepo.deleteByName(templateName);
		logger.log(Level.INFO,"deletion request for template details  Name: {0} completed.",templateName);
		return new ResponseEntity<>("Template Name: "+templateName+" deleted successfully.",HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<TemplateDetailsDTO>> getAllTemplatesByPalletIconLabel(@PathVariable String palletIconLabel) throws CustomException {
		List<TemplateDetailsDTO> detailsDTO = new ArrayList<>();
		try{
			
		logger.log(Level.INFO,"Templates request for Pallet Icon Name: {0} started",palletIconLabel);
		if(!iconRepo.existsByLabel(palletIconLabel))
			throw new PalletIconNotFoundException("Pallet Icon LABEL: "+palletIconLabel+" not found in database.");
		
		List<TemplateDetails> list =tempRepo.findByPalletIconLabel(palletIconLabel);
		
		if(list != null){
			for(TemplateDetails details:list){
				detailsDTO.add(mapper.map(details, TemplateDetailsDTO.class));
			}
		}
		}catch(Exception ex){
			logger.log(Level.SEVERE,"error occoured in creating context menu...");
			 throw new CustomException(ex.getMessage(),ex);
		 }
		return new ResponseEntity<>(detailsDTO,HttpStatus.OK);
	}


	@Override
	public ResponseEntity<List<TemplateDetailsDTO>> getAllTemplates() throws CustomException {
		
		return new ResponseEntity<>(tempService.getAllTemplatesDto(),HttpStatus.OK);
	}

	@Override
	public ResponseEntity<TemplateDetailsDTO> updateTemplate(@Valid TemplateDetailsDTO details) {

		return null;
	}

}
