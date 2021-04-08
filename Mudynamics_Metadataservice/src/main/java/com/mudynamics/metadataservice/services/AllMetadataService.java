package com.mudynamics.metadataservice.services;

import java.util.Map;

import com.mudynamics.metadataservice.dto.AllMetadataDTO;
import com.mudynamics.metadataservice.exceptions.CustomException;

public interface AllMetadataService {

	public Map<String,Object> getAllMudynamicsMetadata()throws CustomException;

	public AllMetadataDTO postAllMetadata(AllMetadataDTO metadataDTO)throws CustomException;
}
