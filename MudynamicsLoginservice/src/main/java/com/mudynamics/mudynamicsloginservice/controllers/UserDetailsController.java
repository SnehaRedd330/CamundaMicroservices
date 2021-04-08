package com.mudynamics.mudynamicsloginservice.controllers;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mudynamics.mudynamicsloginservice.model.UserDTO;
import com.mudynamics.mudynamicsloginservice.payload.UserIdentityAvailability;
import com.mudynamics.mudynamicsloginservice.payload.UserProfile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RefreshScope
@Api(value = "UserDetailsController", tags="")
@RequestMapping(value="/mudynamics/api",produces="application/json")
public interface UserDetailsController {
  
	    @GetMapping("/user/checkUsernameAvailability/{username}")
	    @ApiOperation(
				value = "Respond User Identity Availability",
				notes = "Returns a User Identity Availability",
				response = UserIdentityAvailability.class
		)
		@ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
	    public UserIdentityAvailability checkUsernameAvailability(@PathVariable (value="username",required=true) @NotBlank String username);


	    @GetMapping("/user/checkEmailAvailability/{email}")
	    @ApiOperation(
				value = "Respond User Email Availability",
				notes = "Returns a User Email Availability",
				response = UserIdentityAvailability.class
		)
		@ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
	    public UserIdentityAvailability checkEmailAvailability(@PathVariable(value = "email",required=true)  @NotBlank @Email String email);
	    
	    
	    
	    @GetMapping("/users/{username}")
	    @ApiOperation(
				value = "Respond User profile",
				notes = "Returns a User profile",
				response = UserProfile.class
		)
		@ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
	    public UserProfile getUserProfile(@PathVariable(value = "username")@NotBlank String username);


	    @GetMapping("/getAllUsers")
	    @ApiOperation(
				value = "Respond all Users details",
				notes = "Returns a list of users",
				response = UserDTO.class
		)
		@ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
	    public List<UserDTO> getAllUsers();
	    
	    @DeleteMapping("/deleteUserByUsername/{username}")
	    @ApiOperation(
				value = "delete User profile",
				notes = "Returns nothing"
		)
		@ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
	    public void deleteUser(@PathVariable(value="username") @NotBlank String username);
	    
	    @PutMapping("/updateUserRoleAndCategory")
	    @ApiOperation(
	    		value="update users assigned role",
	    		notes="returns updated user roles.",
	    		response = UserDTO.class
	    		)
	    @ApiResponses(
	    		value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
	    public UserDTO updateUserByRoleAndCategory(@RequestBody UserDTO dto);
	    
	   
	    
}
