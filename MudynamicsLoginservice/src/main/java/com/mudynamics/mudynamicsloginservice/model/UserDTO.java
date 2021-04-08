package com.mudynamics.mudynamicsloginservice.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(Include.NON_NULL)
public class UserDTO {
	
	@JsonProperty(value="name")
	  private String name;
	
	@JsonProperty(value="username")
	  private String username;
	
	@JsonProperty(value="email")
	  private String email;
	
	@JsonProperty(value="roles")
	 private Set<RoleDTO> roles;
	
	@JsonProperty(value="category")
	private Set<ProcessCategoryDTO> category;
	
	@JsonProperty(value="createdAt")
	private String createdAt;
	
	@JsonProperty(value="updatedAt")
	private String updatedAt;
	
	@JsonProperty(value="roles")
	public Set<RoleDTO> getRoles() {
		return roles;
	}
	@JsonProperty(value="roles")
	public void setRoles(Set<RoleDTO> roles) {
		this.roles = roles;
	}
	@JsonProperty(value="name")
	public String getName() {
		return name;
	}
	@JsonProperty(value="name")
	public void setName(String name) {
		this.name = name;
	}
	@JsonProperty(value="username")
	public String getUsername() {
		return username;
	}
	@JsonProperty(value="username")
	public void setUsername(String username) {
		this.username = username;
	}
	@JsonProperty(value="email")
	public String getEmail() {
		return email;
	}
	@JsonProperty(value="email")
	public void setEmail(String email) {
		this.email = email;
	}
	@JsonProperty(value="createdAt")
	public String getCreatedAt() {
		return createdAt;
	}
	@JsonProperty(value="createdAt")
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	@JsonProperty(value="updatedAt")
	public String getUpdatedAt() {
		return updatedAt;
	}
	@JsonProperty(value="updatedAt")
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	@JsonProperty(value="category")
	public Set<ProcessCategoryDTO> getCategory() {
		return category;
	}
	@JsonProperty(value="category")
	public void setCategory(Set<ProcessCategoryDTO> category) {
		this.category = category;
	}
	
	
}
