package com.mudynamics.workflowservice.dto;

import javax.validation.constraints.NotBlank;

public class CamundaServerRequestDTO {
	@NotBlank(message="username must not be blank")
	private String username;
	
	@NotBlank(message="password must not be blank")
	private String password;
	
	@NotBlank(message="camunda server url must not be blank")
	private String url;
	
	
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
	public CamundaServerRequestDTO(@NotBlank(message = "username must not be blank") String username,
			@NotBlank(message = "password must not be blank") String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public CamundaServerRequestDTO() {
		super();
	}
	
	
}
