package com.mudynamics.metadataservice.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@MappedSuperclass
@Data
public abstract class IdGen implements Serializable{

	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		@Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
		 @Column(name = "IdGen_id")
	    private long id;
}
