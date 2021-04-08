package com.mudynamics.metadataservice.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "template_details")
@JsonInclude(Include.NON_NULL)
public class TemplateDetails implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private long tempId;
	 
	 @Column(name="template_id")
	 private String id;
	 
	@Column(name="name",unique=true)
	private String name;
	
	private String objName;
	
	@ElementCollection
	private List<String> appliesTo;
	
	  @ManyToOne(fetch = FetchType.LAZY)
	   @JoinColumn(name = "pallet_icon_id")
	   @JsonIgnore
	    private PalletIcon palletIcon;
	  
	 @ElementCollection(fetch = FetchType.LAZY)
	 @CollectionTable(name = "templatedetails_templateproperties", joinColumns = @JoinColumn(name = "template_details_tempId"))
	 private List<TemplateProperties> properties;
	
	 @JsonInclude(JsonInclude.Include.NON_ABSENT)
	 @Embedded
	 private TemplateConnectorsScopes scopes;

	 
	@Embedded
	private TemplateEntriesVisible entriesVisible;
	
	

	public TemplateConnectorsScopes getScopes() {
		return scopes;
	}



	public void setScopes(TemplateConnectorsScopes scopes) {
		this.scopes = scopes;
	}



	public String getName() {
		return name;
	}



	


	public long getTempId() {
		return tempId;
	}



	public void setTempId(long tempId) {
		this.tempId = tempId;
	}



	public void setId(String id) {
		this.id = id;
	}



	public PalletIcon getPalletIcon() {
		return palletIcon;
	}



	public void setPalletIcon(PalletIcon palletIcon) {
		this.palletIcon = palletIcon;
	}



	public void setName(String name) {
		this.name = name;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public List<String> getAppliesTo() {
		return appliesTo;
	}

	public void setAppliesTo(List<String> appliesTo) {
		this.appliesTo = appliesTo;
	}



	public List<TemplateProperties> getProperties() {
		return properties;
	}

	public void setProperties(List<TemplateProperties> properties) {
		this.properties = properties;
	}

	public TemplateEntriesVisible getEntriesVisible() {
		return entriesVisible;
	}

	public void setEntriesVisible(TemplateEntriesVisible entriesVisible) {
		this.entriesVisible = entriesVisible;
	}


	
}
