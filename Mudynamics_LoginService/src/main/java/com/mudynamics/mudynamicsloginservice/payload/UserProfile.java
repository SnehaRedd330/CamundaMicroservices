package com.mudynamics.mudynamicsloginservice.payload;

public class UserProfile {

	
	    private String username;
	    private String name;
	    private String joinedAt;
	    private String lastActive;
	 
	    public UserProfile( String username, String name, String joinedAt, String lastActive) {
	       
	        this.username = username;
	        this.name = name;
	        this.joinedAt = joinedAt;
	        this.lastActive = lastActive;
	       
	    }
	    
	    

	    public String getLastActive() {
			return lastActive;
		}



		public void setLastActive(String lastActive) {
			this.lastActive = lastActive;
		}



		public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getJoinedAt() {
	        return joinedAt;
	    }

	    public void setJoinedAt(String joinedAt) {
	        this.joinedAt = joinedAt;
	    }

	 
}
