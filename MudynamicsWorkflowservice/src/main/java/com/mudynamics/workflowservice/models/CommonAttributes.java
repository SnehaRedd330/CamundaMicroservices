package com.mudynamics.workflowservice.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import lombok.Data;
@Data
public abstract class CommonAttributes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 @NotBlank
     @Temporal(TemporalType.TIMESTAMP)
     @Column(name="START_TIME_")
     private Date startTime;							   
 										
 	  @Temporal(TemporalType.TIMESTAMP)
 	  @Column(name="END_TIME_")
	  private Date endTime;	
     
	  @Column(name="DURATION_")
      private BigInteger duration;
}
