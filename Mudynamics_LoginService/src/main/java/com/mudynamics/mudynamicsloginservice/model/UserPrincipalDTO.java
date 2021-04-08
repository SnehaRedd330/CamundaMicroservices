package com.mudynamics.mudynamicsloginservice.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(Include.NON_NULL)
public class UserPrincipalDTO {
		
		@JsonProperty("name")
	 	private String name;
		
		@JsonProperty("username")
	    private String username;

		@JsonProperty("email")
	    private String email;

		@JsonProperty("password")
	    private String password;
		
		@JsonProperty("authorities")
	    private Collection<? extends GrantedAuthority> authorities;
		
		@JsonProperty("name")
		public String getName() {
			return name;
		}
		@JsonProperty("name")
		public void setName(String name) {
			this.name = name;
		}
		@JsonProperty("username")
		public String getUsername() {
			return username;
		}
		@JsonProperty("username")
		public void setUsername(String username) {
			this.username = username;
		}
		@JsonProperty("email")
		public String getEmail() {
			return email;
		}
		@JsonProperty("email")
		public void setEmail(String email) {
			this.email = email;
		}
		@JsonProperty("password")
		public String getPassword() {
			return password;
		}
		@JsonProperty("password")
		public void setPassword(String password) {
			this.password = password;
		}
		@JsonProperty("authorities")
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return authorities;
		}
		@JsonProperty("authorities")
		public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
			this.authorities = authorities;
		}
		
		
}
