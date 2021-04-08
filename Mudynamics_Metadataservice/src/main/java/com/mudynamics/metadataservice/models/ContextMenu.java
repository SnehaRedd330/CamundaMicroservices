package com.mudynamics.metadataservice.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="context_menus")
@JsonInclude(Include.NON_NULL)
public class ContextMenu  implements Serializable{


	private static final long serialVersionUID = 1L;

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private long id;
	
	
	@Column(name="name")
	private String name;
	
	@ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "contextMenu_detailList", joinColumns = @JoinColumn(name = "context_menus_id"))
	private List<Details> detailsList;
	
	    @OneToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "pallet_icon_id")
	   private PalletIcon palletIcon;
	
	     public void addTodetailsList(Details details){
	    	 this.detailsList.add(details);
	     }

	public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Details> getDetailsList() {
		return detailsList;
	}

	public void setDetailsList(List<Details> detailsList) {
		this.detailsList = detailsList;
	}

	public PalletIcon getPalletIcon() {
		return palletIcon;
	}

	public void setPalletIcon(PalletIcon palletIcon) {
		this.palletIcon = palletIcon;
	}

	 
	 
}
