package com.mudynamics.metadataservice.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.mudynamics.metadataservice.dto.ContextMenuDTO;
import com.mudynamics.metadataservice.dto.DetailsDTO;
import com.mudynamics.metadataservice.dto.PalletAndContextMenuDTO;
import com.mudynamics.metadataservice.exceptions.CustomException;
import com.mudynamics.metadataservice.models.PalletIcon;

public interface ContextMenuService {
	

	
	
	public ContextMenuDTO getAllContextMenus(PalletIcon icon)throws CustomException;

	public PalletAndContextMenuDTO updateContextMenuByPalletIconLabel(String palletIconLabel,ContextMenuDTO menuDTO);

	public ResponseEntity<String> deletecontextMenu(String palletIconLabel, String contextMenuDetailsLabel)throws CustomException;

	public  Map<String,List<DetailsDTO>> getAllContextMenusDto();
	
	
}
