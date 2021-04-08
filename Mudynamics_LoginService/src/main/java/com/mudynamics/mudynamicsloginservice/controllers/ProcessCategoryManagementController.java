package com.mudynamics.mudynamicsloginservice.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mudynamics.mudynamicsloginservice.model.AvailableUsersDTO;
import com.mudynamics.mudynamicsloginservice.model.ProcessCategoryDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RefreshScope
@Api(value = "ProcessCategoryManagementController", tags="")
@RequestMapping(value="/mudynamics/api",produces="application/json")
public interface ProcessCategoryManagementController 
{
	 @GetMapping("/getAllWorkflowCategory")
	    @ApiOperation(
				value = "Respond list of User Roles.",
				notes = "Returns a list of User Roles.",
				response = String.class
		)
		@ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
	    public ResponseEntity<List<String>> getAllWorkflowCategories(@RequestParam(required = false,defaultValue = " ") String userId);
	 
	 
	 @DeleteMapping("/deleteProcessCategory/{userId}/{processCategory}")
	 @ApiOperation(
				value = "delete role from database",
				notes = "delete role from database"
				
				
		)
		@ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
	    public void deleteProcessCategory(@PathVariable(value="userId",required=true) @NotBlank(message="username must not be blank") String userId,@PathVariable(value="processCategory" ,required = true) @NotBlank String processCategory);
	 
	
	 @PostMapping("/createProcessCategory/{userId}")
	 @ApiOperation(
			 value="respond newly created role.",
			 notes="creates new role.",
			 response= ProcessCategoryDTO.class
			 )
	 @ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
	 public ResponseEntity<ProcessCategoryDTO> createProcessCategory(@PathVariable(value="userId",required=true) @NotBlank(message="username must not be blank") String userId,@Valid @RequestBody ProcessCategoryDTO dto);
	 
	 @GetMapping("/getAllUsersByCategory/{category}")
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
	    public ResponseEntity<Set<AvailableUsersDTO>> getAllUsersByCategory(@PathVariable(value="category",required=true)@NotBlank(message="category must not be blank") String category);
	
}
