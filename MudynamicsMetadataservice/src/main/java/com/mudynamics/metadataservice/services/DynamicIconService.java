package com.mudynamics.metadataservice.services;

import java.util.List;

import com.mudynamics.metadataservice.dto.PalletIconDTO;
import com.mudynamics.metadataservice.exceptions.CustomException;
import com.mudynamics.metadataservice.models.PalletIcon;

public interface DynamicIconService {

	public  List<PalletIconDTO> getDynamicImageIcons()throws CustomException;

	public PalletIconDTO updateDynamicIconByPalletIconLabel(PalletIconDTO iconDTO);

	void deletePalletIcon(PalletIcon icon)throws  CustomException;
	

	
}
