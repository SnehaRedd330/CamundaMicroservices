package com.mudynamics.workflowservice.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UrlDetailsDTO {
	@JsonProperty("component")
	@NotBlank
	private  String  component;
	
	@JsonProperty("envmt")
	@NotBlank
	private String envmt;
	
	@JsonProperty("url")
	@NotBlank
	private String url;
	
	@JsonProperty("username")
	@NotBlank
	private String username;
	
	@JsonProperty("password")
	@NotBlank
	private String password;
	private String lastModifiedBy;

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}


	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public String getEnvmt() {
		return envmt;
	}

	public void setEnvmt(String envmt) {
		this.envmt = envmt;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	
}
