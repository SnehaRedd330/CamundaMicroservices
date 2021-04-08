package com.mudynamics.mudynamicsloginservice.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoleDTO {
	
	 @JsonProperty(value="name")
	 @NotBlank
	 @Size(min = 3, max = 15, message=" Role Name field size should be in range of 3 to 20 chars.")
	 private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
