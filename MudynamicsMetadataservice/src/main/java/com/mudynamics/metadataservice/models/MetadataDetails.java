package com.mudynamics.metadataservice.models;

public class MetadataDetails {

	private PalletIcon icon;
	
	private ContextMenu contextMenu;
	
	private TemplateDetails templateDetails;

	public PalletIcon getIcon() {
		return icon;
	}

	public void setIcon(PalletIcon icon) {
		this.icon = icon;
	}

	public ContextMenu getContextMenu() {
		return contextMenu;
	}

	public void setContextMenu(ContextMenu contextMenu) {
		this.contextMenu = contextMenu;
	}

	public TemplateDetails getTemplateDetails() {
		return templateDetails;
	}

	public void setTemplateDetails(TemplateDetails templateDetails) {
		this.templateDetails = templateDetails;
	}
	
	
}
