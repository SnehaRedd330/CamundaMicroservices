package com.mudynamics.metadataservice.dto;

import java.util.List;

public class ContextMenuDTO {

	private String name;
	
	private List<DetailsDTO> detailsList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public List<DetailsDTO> getDetailsList() {
		return detailsList;
	}

	public void setDetailsList(List<DetailsDTO> detailsList) {
		this.detailsList = detailsList;
	}

	@Override
	public String toString() {
		return "ContextMenuDTO [name=" + name + ", detailsList=" + detailsList + "]";
	}
	
	
}
