package com.mudynamics.metadataservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PalletAndContextMenuDTO {

	private PalletIconDTO iconDTO;
	
	private ContextMenuDTO contextMenuDTO;

	public PalletIconDTO getIconDTO() {
		return iconDTO;
	}

	public void setIconDTO(PalletIconDTO iconDTO) {
		this.iconDTO = iconDTO;
	}

	public ContextMenuDTO getContextMenuDTO() {
		return contextMenuDTO;
	}

	public void setContextMenuDTO(ContextMenuDTO contextMenuDTO) {
		this.contextMenuDTO = contextMenuDTO;
	}
	
	
}
