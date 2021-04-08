package com.mudynamics.metadataservice.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mudynamics.metadataservice.dto.PalletIconDTO;
import com.mudynamics.metadataservice.exceptions.CustomException;
import com.mudynamics.metadataservice.exceptions.PalletIconNotFoundException;
import com.mudynamics.metadataservice.models.PalletIcon;
import com.mudynamics.metadataservice.repositories.PaletteIconRepository;




@Service
public class DynamicIconServiceImpl implements DynamicIconService {
	
	
	 @Autowired
	 private ModelMapper modelMapper;
	
	@Autowired
	private PaletteIconRepository iconRepo;
	
	
	Logger logger = Logger.getLogger(DynamicIconServiceImpl.class.getName());
	
	
	@Override
	public List<PalletIconDTO> getDynamicImageIcons() throws CustomException {
		if(iconRepo.findAll().isEmpty())
			return new ArrayList<>();
		
		return iconRepo.findAll().parallelStream().map(icon -> modelMapper.map(icon, PalletIconDTO.class)).collect(Collectors.toList());
	}






	@Override
	public PalletIconDTO updateDynamicIconByPalletIconLabel(PalletIconDTO iconDTO){
		
		PalletIcon icon =modelMapper.map(iconDTO, PalletIcon.class);
		
		PalletIcon palletIcon =iconRepo.findByLabel(icon.getLabel()).orElseThrow(() ->  new PalletIconNotFoundException("Pallet Icon LABEL: " + iconDTO.getLabel() + " not found in databse.") );
		
			palletIcon.setImage(icon.getImage());
			palletIcon.setActivity(icon.getActivity());
			palletIcon.setFileType(icon.getFileType());
			palletIcon.setUpdatedAt(new Date());
		
		return modelMapper.map(iconRepo.save(palletIcon), PalletIconDTO.class);
	}




	@Override
	@Transactional
	public void deletePalletIcon(PalletIcon icon) throws CustomException {
		try{
			iconRepo.deleteById(icon.getId());
		}catch(Exception ex){
			throw new CustomException("something went wrong in Pallet Icon deletion operation..",ex);
		}
		
		
	}
	

}
