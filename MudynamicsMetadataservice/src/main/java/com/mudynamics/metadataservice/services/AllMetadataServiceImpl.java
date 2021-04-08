package com.mudynamics.metadataservice.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.mudynamics.metadataservice.Constants.TEMPLATE_DETAILS_LIST;
import static com.mudynamics.metadataservice.Constants.CONTEXT_MENU_MAP;
import static com.mudynamics.metadataservice.Constants.PALLET_ICON_LIST;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mudynamics.metadataservice.dto.AllMetadataDTO;
import com.mudynamics.metadataservice.dto.ContextMenuDTO;
import com.mudynamics.metadataservice.dto.DetailsDTO;
import com.mudynamics.metadataservice.dto.PalletIconDTO;
import com.mudynamics.metadataservice.dto.TemplateDetailsDTO;
import com.mudynamics.metadataservice.exceptions.CustomException;
import com.mudynamics.metadataservice.exceptions.DuplicateContextMenuException;
import com.mudynamics.metadataservice.exceptions.DuplicatePalletIconException;
import com.mudynamics.metadataservice.exceptions.InvalidInputException;
import com.mudynamics.metadataservice.models.ContextMenu;
import com.mudynamics.metadataservice.models.PalletIcon;
import com.mudynamics.metadataservice.models.TemplateDetails;
import com.mudynamics.metadataservice.repositories.ContextMenuRepository;
import com.mudynamics.metadataservice.repositories.PaletteIconRepository;
import com.mudynamics.metadataservice.repositories.TemplateDetailsRepository;

@Service
public class AllMetadataServiceImpl implements AllMetadataService {
	
	@Autowired
	private ModelMapper mapper;
	
	private DynamicIconService iconService;
	
	@Autowired
	public void setIconService(DynamicIconService iconService) {
		this.iconService = iconService;
	}
	
	private ContextMenuService contextMenuService;
	
	@Autowired
	public void setContextMenuService(ContextMenuService contextMenuService) {
		this.contextMenuService = contextMenuService;
	}
	private TemplateDetailService tempService;

	@Autowired
	public void setTempService(TemplateDetailService tempService) {
		this.tempService = tempService;
	}
	
	Logger logger = Logger.getLogger(AllMetadataServiceImpl.class.getName());
	
	@Autowired
	private PaletteIconRepository iconRepository;
	@Autowired
	private ContextMenuRepository menuRepository;
	@Autowired
	private TemplateDetailsRepository detailsRepository;

	
	
	@Override
	public Map<String, Object> getAllMudynamicsMetadata() throws CustomException{
		Map<String, Object> listOfAllItems= new HashMap<>();
		List<PalletIconDTO> iconDTOs=iconService.getDynamicImageIcons();
		Map<String,List<DetailsDTO>> map=contextMenuService.getAllContextMenusDto();
		List<TemplateDetailsDTO> detailsDTOs =tempService.getAllTemplatesDto();
				
	
		if(iconDTOs != null && !iconDTOs.isEmpty())
			listOfAllItems.put(PALLET_ICON_LIST,iconDTOs );
		
		if(map != null && !map.isEmpty())
			listOfAllItems.put(CONTEXT_MENU_MAP, map);
		
		if(detailsDTOs != null && !detailsDTOs.isEmpty())
			listOfAllItems.put(TEMPLATE_DETAILS_LIST, detailsDTOs);
		
	
return listOfAllItems;
}

	@Override
	@Transactional
	public AllMetadataDTO postAllMetadata(AllMetadataDTO metadataDTO){
		
		if("bpmn:CallActivity".equals(metadataDTO.getActivity())){
			ContextMenuDTO contextMenu=metadataDTO.getContextMenus();
			
			if(menuRepository.existsByName(contextMenu.getName())){
				throw new DuplicateContextMenuException("Duplicate Context Menu Label: "+metadataDTO.getContextMenus().getName()+" already exists in database.");
			}
		}
			
			if(iconRepository.existsByLabel(metadataDTO.getLabel()))
				throw new DuplicatePalletIconException("Duplicate Pallet Icon label: "+metadataDTO.getLabel()+" provided error.");
			
			for(TemplateDetailsDTO detailsDTO:metadataDTO.getTemplates()){
				if(detailsRepository.existsByName(detailsDTO.getName()))
					throw new InvalidInputException("Duplicate Template Name: "+detailsDTO.getName()+" already exists.");
			}
			
		PalletIcon icon = new PalletIcon();
		icon.setActivity(metadataDTO.getActivity());
		icon.setFileType(metadataDTO.getFileType());
		icon.setLabel(metadataDTO.getLabel());
		icon.setImage(metadataDTO.getImage());
		
    logger.log( Level.INFO,"saving metadata for {0} task in database.",metadataDTO.getActivity());
    
		saveContextMenuAndTemplates(icon,metadataDTO.getContextMenus(),metadataDTO.getTemplates());
		logger.log( Level.INFO,"metadata posted successfully.");

		
		return metadataDTO;
	}


	private void saveContextMenuAndTemplates(PalletIcon tempiconDetails, ContextMenuDTO contextMenus,List<TemplateDetailsDTO>templateDetailsDTOs) {
		logger.log(Level.INFO,"saving context menus for BPMN:CallActivity task in database.");
		
		PalletIcon iconDetails =tempiconDetails;
		List<TemplateDetails> details= new ArrayList<>();
		
		
		for(TemplateDetailsDTO detailsDTO:templateDetailsDTOs){
			TemplateDetails templateDetails =mapper.map(detailsDTO, TemplateDetails.class);
			details.add(templateDetails);
		}

		ContextMenu contextMenu =mapper.map(contextMenus, ContextMenu.class);
		iconDetails.setMenu(contextMenu);
		contextMenu.setPalletIcon(iconDetails);

		iconDetails.setTemplateDetails(details);
		
		for(TemplateDetails templateDetails:details){
			templateDetails.setPalletIcon(iconDetails);	
		}
		iconRepository.save(iconDetails);
		menuRepository.save(contextMenu);

		detailsRepository.saveAll(details);
		logger.log(Level.INFO,"saving templates for BPMN:CallActivity task in database."); 
		
	}
	
}