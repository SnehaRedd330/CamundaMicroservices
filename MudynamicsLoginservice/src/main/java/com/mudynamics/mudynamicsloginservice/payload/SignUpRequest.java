package com.mudynamics.mudynamicsloginservice.payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpRequest {

	@NotBlank(message = "name field cannot be null or whitespace")
    @Size(min = 4, max = 40, message="Name field size should be in range of 4 to 30 chars.")
    private String name;

    @NotBlank(message = "user name cannot be null or whitespace")
    @Size(min = 3, max = 15, message="Name field size should be in range of 4 to 30 chars.")
    private String username;

    @NotBlank(message = "email cannot be null or whitespace")
    @Size(max = 40, message="email field size should be max to 40 chars.")
    private String email;

    @NotBlank(message = "password cannot be null or whitespace")
    @Size(min = 6, max = 20, message="password field size should be in range of 6 to 20 chars..")
    private String password;
    
    @NotBlank(message = "category cannot be null,atleast one expected.")
    private String category;
     
   

	public SignUpRequest() {
		super();
	}

	public SignUpRequest(
			@NotBlank(message = "name field cannot be null or whitespace") @Size(min = 4, max = 40, message = "Name field size should be in range of 4 to 30 chars.") String name,
			@NotBlank(message = "user name cannot be null or whitespace") @Size(min = 3, max = 15, message = "Name field size should be in range of 4 to 30 chars.") String username,
			@NotBlank(message = "email cannot be null or whitespace") @Size(max = 40, message = "email field size should be max to 40 chars.") String email,
			@NotBlank(message = "password cannot be null or whitespace") @Size(min = 6, max = 20, message = "password field size should be in range of 6 to 20 chars..") String password,
			@NotBlank(message = "category cannot be null,atleast one expected.") String category) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	
    
}
