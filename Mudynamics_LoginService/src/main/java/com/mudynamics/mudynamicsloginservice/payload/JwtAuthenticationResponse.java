package com.mudynamics.mudynamicsloginservice.payload;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationResponse {

	    private String accessToken;
	    private String tokenType = "Bearer";
	    private String usernameOrEmail;
	    private Collection<? extends GrantedAuthority> auth;
	    private Set<String> processCategories = new HashSet<String>();
	  



		public JwtAuthenticationResponse(String accessToken, String usernameOrEmail,
				Collection<? extends GrantedAuthority> auth, Set<String> processCategories) {
			super();
			this.accessToken = accessToken;
			this.usernameOrEmail = usernameOrEmail;
			this.auth = auth;
			this.processCategories = processCategories;
		}

		public String getAccessToken() {
	        return accessToken;
	    }

	    public void setAccessToken(String accessToken) {
	        this.accessToken = accessToken;
	    }

	    public String getTokenType() {
	        return tokenType;
	    }

	    public void setTokenType(String tokenType) {
	        this.tokenType = tokenType;
	    }

		public String getUsernameOrEmail() {
			return usernameOrEmail;
		}

		public void setUsernameOrEmail(String usernameOrEmail) {
			this.usernameOrEmail = usernameOrEmail;
		}

		public Collection<? extends GrantedAuthority> getAuth() {
			return auth;
		}

		public void setAuth(Collection<? extends GrantedAuthority> auth) {
			this.auth = auth;
		}

		public Set<String> getProcessCategories() {
			return processCategories;
		}

		public void setProcessCategories(Set<String> processCategories) {
			this.processCategories = processCategories;
		}
	    
	    
}
