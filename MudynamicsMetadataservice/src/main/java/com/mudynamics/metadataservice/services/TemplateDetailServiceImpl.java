package com.mudynamics.metadataservice.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mudynamics.metadataservice.dto.TemplateDetailsDTO;
import com.mudynamics.metadataservice.exceptions.CustomException;
import com.mudynamics.metadataservice.exceptions.PalletIconNotFoundException;
import com.mudynamics.metadataservice.models.PalletIcon;
import com.mudynamics.metadataservice.models.TemplateDetails;
import com.mudynamics.metadataservice.repositories.PaletteIconRepository;
import com.mudynamics.metadataservice.repositories.TemplateDetailsRepository;

@Service
public class TemplateDetailServiceImpl implements TemplateDetailService{
	
	@Autowired
	private TemplateDetailsRepository tempRepo;
	
	@Autowired
	private PaletteIconRepository iconRepo;
	
	 @Autowired
	private ModelMapper modelMapper;
	 

	@Override
	public List<TemplateDetails> getAllTemplate(String contextMenuLabel) {
		
		return tempRepo.findByPalletIconLabel(contextMenuLabel);
	}


	@Override
	public List<TemplateDetailsDTO> getAllTemplatesDto() throws CustomException {
		List<TemplateDetailsDTO> templateDetailsDTOs = new ArrayList<>();
		
		
		try{
		
				List<TemplateDetails> details =tempRepo.findAll();
				for(TemplateDetails templateDetails:details){
					TemplateDetailsDTO detailsDTO=null;
					
					TemplateDetailsDTO templateDetailsDTO=modelMapper.map(templateDetails, TemplateDetailsDTO.class);
					templateDetailsDTO.setid("org.camunda."+templateDetails.getName().replaceAll("\\s",""));
					
					if(templateDetailsDTO.getScopes().getCamundaConnector().getProperties().isEmpty()){
						Gson gson = new GsonBuilder().create();
						String json = gson.toJson(templateDetailsDTO);
						JsonObject jsonObj =gson.fromJson(json, JsonObject.class);
						jsonObj.remove("scopes");
						 detailsDTO=gson.fromJson(jsonObj, TemplateDetailsDTO.class);
					}
						if(detailsDTO!=null)
							templateDetailsDTOs.add(detailsDTO);
						else
							templateDetailsDTOs.add(templateDetailsDTO);
				}
		}catch(Exception ex){
			 throw new CustomException(ex.getMessage(),ex);
		 }
		return templateDetailsDTOs;
	}


	@Override
	public void deleteTemplateDetailsByName(String palletIconLabel, String templateName){
		PalletIcon icon=iconRepo.findByLabel(palletIconLabel).orElseThrow(() ->  new PalletIconNotFoundException("Pallet Icon LABEL: " + palletIconLabel + " not found in databse.") );
		TemplateDetails deletionTemplateData=null;
		for(TemplateDetails item:icon.getTemplateDetails()){
			if(templateName.equals(item.getName())){
				deletionTemplateData = item;
			}
		}
		if(deletionTemplateData != null){
			icon.getTemplateDetails().remove(deletionTemplateData);
		}
	}




}
