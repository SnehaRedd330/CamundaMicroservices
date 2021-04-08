package com.mudynamics.metadataservice.services;

import static com.mudynamics.metadataservice.Constants.NO_CONTEXTMENU_FOUND;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mudynamics.metadataservice.dto.ContextMenuDTO;
import com.mudynamics.metadataservice.dto.DetailsDTO;
import com.mudynamics.metadataservice.dto.PalletAndContextMenuDTO;
import com.mudynamics.metadataservice.exceptions.CustomException;
import com.mudynamics.metadataservice.exceptions.NoContextMenuFoundException;
import com.mudynamics.metadataservice.exceptions.PalletIconNotFoundException;
import com.mudynamics.metadataservice.models.ContextMenu;
import com.mudynamics.metadataservice.models.Details;
import com.mudynamics.metadataservice.models.PalletIcon;
import com.mudynamics.metadataservice.repositories.ContextMenuRepository;
import com.mudynamics.metadataservice.repositories.PaletteIconRepository;

@Service
public class ContextMenuServiceImpl implements ContextMenuService {

	private static Logger logger = Logger.getLogger(DynamicIconServiceImpl.class.getName());
	
	private ContextMenuRepository contextMenuRepository;
	
	 @Autowired
	 private ModelMapper modelMapper;
	 
	 @Autowired
	 public void setContextMenuRepository(ContextMenuRepository contextMenuRepository) {
		this.contextMenuRepository = contextMenuRepository;
	 }
	
	@Autowired
	private PaletteIconRepository iconRepo;

	@Override
	public ContextMenuDTO getAllContextMenus(PalletIcon icon) throws  CustomException {
		ContextMenuDTO menuDTOs = new ContextMenuDTO();
		try{
		
		if(icon.getMenu() == null)
				throw new NoContextMenuFoundException(NO_CONTEXTMENU_FOUND+icon.getLabel());
		
		ContextMenu contextMenu =icon.getMenu();
		menuDTOs =modelMapper.map(contextMenu, ContextMenuDTO.class);
		}catch(Exception ex){
			logger.log(Level.SEVERE,"error occoured in getting All context menus...");
			 throw new CustomException(ex.getMessage(),ex);
		 }
		 return menuDTOs;
	}



	@Override
	public PalletAndContextMenuDTO updateContextMenuByPalletIconLabel(String palletIconLabel, ContextMenuDTO menuDTO){	
		PalletIcon updatedPalletIcon;
		PalletIcon icon= iconRepo.findByLabel(palletIconLabel).orElseThrow(() -> new PalletIconNotFoundException("Palette Icon not found in database."));
		
				for(DetailsDTO detailsDTO:menuDTO.getDetailsList()){
						
					icon.getMenu().getDetailsList().forEach(details -> {
						
						if(detailsDTO.getLabel().equals(icon.getLabel())){
							icon.getMenu().getDetailsList().remove(details);
						}
						else
							icon.getMenu().getDetailsList().add(details);
					});				
				}
					updatedPalletIcon=iconRepo.save(icon);
		return modelMapper.map(updatedPalletIcon, PalletAndContextMenuDTO.class);
	       	}



	@Override
	@Transactional
	public ResponseEntity<String> deletecontextMenu(String palletIconLabel, String contextMenuDetailsLabel) throws CustomException {
		 String status;
		 Details deletionDetailsData=null;
		 PalletIcon icon=iconRepo.findByLabel(palletIconLabel).orElseThrow(() ->  new PalletIconNotFoundException("Pallet Icon LABEL: " + palletIconLabel + " not found in databse.") );
		
		 for(Details item:icon.getMenu().getDetailsList()){
			  if( contextMenuDetailsLabel.equals(item.getLabel()))
		     deletionDetailsData =item;
		 }
		
		 if(deletionDetailsData != null){
		 icon.getMenu().getDetailsList().remove(deletionDetailsData);
		 status= "Pallet Icon Label:"+palletIconLabel+" context Menu details Label:"+contextMenuDetailsLabel+" deleted successfully.";
		 }else status="Pallet Icon Label:"+palletIconLabel+" context Menu details Label:"+contextMenuDetailsLabel+"not available in database.";
			
		 return new ResponseEntity<>(status,HttpStatus.OK);
	}



	@Override
	public Map<String,List<DetailsDTO>> getAllContextMenusDto() {
		Map<String,List<DetailsDTO>> map= new HashMap<>();
		List<ContextMenu> contextMenus =contextMenuRepository.findAll();
		for(ContextMenu contextMenu:contextMenus){
			ContextMenuDTO contextMenuDTO=modelMapper.map(contextMenu, ContextMenuDTO.class);
			if(contextMenuDTO.getName()!=null && !contextMenuDTO.getDetailsList().isEmpty()){
			map.put(contextMenuDTO.getName(), contextMenuDTO.getDetailsList());
			}
		}
		
		return map;
	}



}
	

	


	

