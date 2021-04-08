package com.mudynamics.mudynamicsloginservice.controllers;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mudynamics.mudynamicsloginservice.exceptions.RoleDeletionNotAllowedException;
import com.mudynamics.mudynamicsloginservice.model.AvailableUsersDTO;
import com.mudynamics.mudynamicsloginservice.model.RoleDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RefreshScope
@Api(value = "RoleManagementController", tags="")
@RequestMapping(value="/mudynamics/api",produces="application/json")
public interface RoleManagementController {

	 @GetMapping("/getAllRoles")
	    @ApiOperation(
				value = "Respond list of User Roles.",
				notes = "Returns a list of User Roles.",
				response = RoleDTO.class
		)
		@ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
	    public Set<RoleDTO> getAllRoles();
	 
	 
	 @DeleteMapping("/deleteRole/{role}")
	 @ApiOperation(
				value = "delete role from database",
				notes = "delete role from database"
				
		)
		@ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
	    public void deleteRole(@PathVariable(value="role" ,required = true) @NotBlank String role)throws RoleDeletionNotAllowedException;
	 
	
	 @PostMapping("/createRole")
	 @ApiOperation(
			 value="respond newly created role.",
			 notes="creates new role.",
			 response=RoleDTO.class
			 )
	 @ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
	 public RoleDTO createRole(@Valid @RequestBody RoleDTO dto);
	 
	 @GetMapping("/getAllUsersByRole/{role}")
	    @ApiOperation(
				value = "Respond list of User Roles.",
				notes = "Returns a list of User Roles.",
				response = AvailableUsersDTO.class
		)
		@ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
	    public Set<AvailableUsersDTO> getAllUsersByRole(@PathVariable(value="role",required = true) @NotBlank String role);
}
