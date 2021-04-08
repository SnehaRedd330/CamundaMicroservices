package com.mudynamics.workflowservice.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CamundaServerStatusDTO {

	private String authenticatedUser;

	private String groups;
	private String tenants;
	
	private boolean authenticated;

	public String getAuthenticatedUser() {
		return authenticatedUser;
	}

	public void setAuthenticatedUser(String authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public String getTenants() {
		return tenants;
	}

	public void setTenants(String tenants) {
		this.tenants = tenants;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
	
	
	
}
