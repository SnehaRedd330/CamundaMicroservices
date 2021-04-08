package com.mudynamics.metadataservice.models;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Entity
@Table(name = "palette_icon")
@JsonInclude(Include.NON_NULL)
public class PalletIcon extends AuditModel {

	
	

	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private long id;
		
	
	 @Column(name = "Palette_Icon_label", unique = true)
	 private String label;
		
	 @Column(name = "Palette_Icon_type")
	 private String fileType;
	 
	
	 @Column(name = "BPMN_Activity")
	 private String activity;
	
	
	 @Lob
	 @Column(name="Palette_Icon_image")
	 private byte[] image;
	 
	 
	 @OneToOne(mappedBy = "palletIcon", cascade = CascadeType.ALL, orphanRemoval = true)
	 private ContextMenu menu= new ContextMenu();
	 
	 
	@OneToMany(mappedBy = "palletIcon", orphanRemoval = true)
	private List<TemplateDetails> templateDetails= new ArrayList<>();
		
	 	 
	public PalletIcon() {
		super();
	}

	public void addToTemplateDetailsList(TemplateDetails templateDetails){
		this.templateDetails.add(templateDetails);
	}
	public PalletIcon(@NotNull String label, String fileType, @NotNull String activity, @NotNull byte[] image) {
		super();
		this.label = label;
		this.fileType = fileType;
		this.activity = activity;
		this.image = image;
	}
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public ContextMenu getMenu() {
		return menu;
	}



	public void setMenu(ContextMenu menu) {
		this.menu = menu;
	}


	public List<TemplateDetails> getTemplateDetails() {
		return templateDetails;
	}

	public void setTemplateDetails(List<TemplateDetails> templateDetails) {
		this.templateDetails = templateDetails;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	

	@Override
	public String toString() {
		return "PalletIcon [paltetIconId=" + id + ", label=" + label + ", fileType=" + fileType
				+ ", activity=" + activity + ", image=" + image + "]";
	}

	

	
	

	
	


}
