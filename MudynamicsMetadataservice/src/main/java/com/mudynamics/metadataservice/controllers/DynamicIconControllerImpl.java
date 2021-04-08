package com.mudynamics.metadataservice.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mudynamics.metadataservice.dto.AllMetadataDTO;
import com.mudynamics.metadataservice.dto.PalletIconDTO;
import com.mudynamics.metadataservice.exceptions.CustomException;
import com.mudynamics.metadataservice.exceptions.PalletIconNotFoundException;
import com.mudynamics.metadataservice.models.PalletIcon;
import com.mudynamics.metadataservice.repositories.PaletteIconRepository;
import com.mudynamics.metadataservice.services.AllMetadataService;
import com.mudynamics.metadataservice.services.DynamicIconService;


@RestController
public class DynamicIconControllerImpl implements DynamicIconController {

	private static Logger logger = Logger.getLogger(TemplateDetailsControllerImpl.class.getName());
	
	private DynamicIconService iconService;
	
	@Autowired
	public void setIconService(DynamicIconService iconService) {
		this.iconService = iconService;
	}

	@Autowired
	private PaletteIconRepository iconRepo;
	
	
	@Autowired
	private AllMetadataService metadataService;
	
	
	
	@Override
	@Transactional
	public ResponseEntity<String> deleteDynamicIconByLabel(@PathVariable String  label) throws CustomException{	
		logger.log(Level.INFO,"deletion request for pallet Icon LABEL: {0} started",label);
		if(!iconRepo.existsByLabel(label))
			throw new PalletIconNotFoundException("Pallet Icon LABEL: "+label+" not found in database.");
		Optional<PalletIcon> palletIcon =iconRepo.findByLabel(label);
		if(palletIcon.isPresent())
			iconRepo.delete(palletIcon.get());
		
		logger.log(Level.INFO,"deletion request for pallet Icon completed.");
		return new ResponseEntity<>("Pallet Icon "+label+" deleted successfully",new HttpHeaders(), HttpStatus.OK);
			
	}

	@Override
	public ResponseEntity<List<PalletIconDTO>> getAllPaletteIconImageData() throws CustomException {
		
		 
		return new ResponseEntity<>( iconService.getDynamicImageIcons(), HttpStatus.OK);
	}


	@Override
	public ResponseEntity<PalletIconDTO> updateDynamicIcon(@Valid @RequestBody PalletIconDTO iconDTO){
		logger.log(Level.INFO,"request for updating Pallet Icon  Label: {0} initiated..",iconDTO.getLabel());
		PalletIconDTO palletIconDTO = iconService.updateDynamicIconByPalletIconLabel(iconDTO);
		logger.log(Level.INFO,"request for updating Pallet Icon  Label: {0} completed..",palletIconDTO.getLabel());
		return new ResponseEntity<>(palletIconDTO,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllMetada() throws CustomException{
		logger.log(Level.INFO,"collecting all metadata including Pallet Icons, Context Menus, Templates..");
	return new ResponseEntity<>(metadataService.getAllMudynamicsMetadata(),HttpStatus.OK);
		
	}


	@Override
	public ResponseEntity<AllMetadataDTO> postAllMetadata(@RequestBody AllMetadataDTO metadataDTO)throws CustomException {
		logger.log(Level.INFO,"posting all metadata request initiated");
		return new ResponseEntity<>(metadataService.postAllMetadata(metadataDTO), new HttpHeaders(), HttpStatus.CREATED);
	}


	


}
