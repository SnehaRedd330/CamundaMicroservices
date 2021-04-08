package com.mudynamics.mudynamicsloginservice.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ProcessCategoryDTO {
	
	@NotBlank(message="category must not be blank..")
	@JsonProperty(value="category")
	private String category;
	
	@JsonProperty(value="category")
	public String getCategory() {
		return category;
	}

	@JsonProperty(value="category")
	public void setCategory(String category) {
		this.category = category;
	}
	
	
}
