package com.mudynamics.metadataservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mudynamics.metadataservice.dto.ContextMenuDTO;
import com.mudynamics.metadataservice.dto.DetailsDTO;
import com.mudynamics.metadataservice.dto.PalletAndContextMenuDTO;
import com.mudynamics.metadataservice.exceptions.CustomException;
import com.mudynamics.metadataservice.exceptions.PalletIconNotFoundException;
import com.mudynamics.metadataservice.repositories.PaletteIconRepository;
import com.mudynamics.metadataservice.services.ContextMenuService;


@RestController
public class ContextMenuControllerImpl implements ContextMenuController{

	private static Logger logger = Logger.getLogger(ContextMenuControllerImpl.class.getName());
	
	@Autowired
	private PaletteIconRepository iconRepository;
	
	private ContextMenuService contextMenuService;
	
	@Autowired
	public void setContextMenuService(ContextMenuService contextMenuService) {
		this.contextMenuService = contextMenuService;
	}
	
	
	@Override
	public ResponseEntity<ContextMenuDTO> getAllContextMenusByPalletIconLabel(@PathVariable String palletIconLabel) throws CustomException {
		ContextMenuDTO contextMenuDTOs = new ContextMenuDTO();
		logger.log(Level.INFO, "returns All context menus for Pallet icon label: {0}", palletIconLabel);
		try{
			if(!iconRepository.existsByLabel(palletIconLabel)){
				logger.log(Level.SEVERE,"Pallet Icon Label: {0} not found in database.",palletIconLabel);
				throw new PalletIconNotFoundException("Pallet Icon Label: "+palletIconLabel+" not found in database.");
			}

			 contextMenuDTOs =contextMenuService.getAllContextMenus(iconRepository.findByLabel(palletIconLabel).orElseThrow(() -> new PalletIconNotFoundException("Pallet Icon LABEL: " + palletIconLabel + " not found in databse.")));
			
		}catch(Exception ex){
			logger.log(Level.SEVERE,"error occoured in get all context menu by pallet icon label...");
			 throw new CustomException(ex.getMessage(),ex);
		 }
		return new ResponseEntity<>(contextMenuDTOs,HttpStatus.OK);
	}


	@Override
	public ResponseEntity<PalletAndContextMenuDTO> updateContextMenu(@PathVariable String palletIconLabel, @RequestBody ContextMenuDTO menuDTO) throws CustomException{
		try{
			if(!iconRepository.existsByLabel(palletIconLabel)){
				logger.log(Level.SEVERE,"Pallet Icon Label: {0} not found in database.",palletIconLabel);
				throw new PalletIconNotFoundException("Pallet Icon Label: "+palletIconLabel+" not found in database.");
			}
			contextMenuService.updateContextMenuByPalletIconLabel(palletIconLabel, menuDTO);
		}catch(Exception ex){
			logger.log(Level.SEVERE,"error occoured in update context menu by pallet icon label...");
			 throw new CustomException(ex.getMessage(),ex);
		}
		return null;
	}


	@Override
	@Transactional
	public ResponseEntity<String> deleteContextMenuDetailsByPalletIconLabel(@PathVariable String palletIconLabel, @PathVariable String contextMenuDetailsLabel) throws CustomException {
		
		return contextMenuService.deletecontextMenu(palletIconLabel,contextMenuDetailsLabel);
	}


	@Override
	public ResponseEntity<Map<String,List<DetailsDTO>>> getAllContextMenus() throws CustomException {
		Map<String, List<DetailsDTO>> map = new HashMap<>();
		try{
		 map=contextMenuService.getAllContextMenusDto();
		}catch(Exception ex){
			logger.log(Level.SEVERE,"error occoured in get all context menu by pallet icon id...");
			 throw new CustomException(ex.getMessage(),ex);
		 }
		return new ResponseEntity<>(map,HttpStatus.OK);
	}

	

}
