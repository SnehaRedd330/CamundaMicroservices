package com.mudynamics.workflowservice.controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mudynamics.workflowservice.dto.UrlDetailsDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="vTMComponentsURLController")
@RequestMapping(value="/mudynamics/workflowdetail/v1",produces="application/json")
public interface MuDynamicsDeploymentURLController {

	/**
	 * Rest Service method details 
	 * @author SJ00495527
	 *
	 */
	
	/**
	 * Create New Component and It's URL details 
	 * @return Response of newly created Component
	 * @throws InvalidDataException 
	 */
	@PostMapping("/createDeploymentServerDetails")
	@ApiOperation(
			value = "Respond new created Component and URL Details",
			notes = "Returns Response ststus.",
			response = UrlDetailsDTO.class
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					
					})
	public ResponseEntity<UrlDetailsDTO> createUrl( @Valid @RequestBody UrlDetailsDTO urldtl) ;
	
	
	/**
	 * to get All Component and It's URL details 
	 * @return Response of All Component details
	 * @throws InsufficientInputDataException, NoUrlDetailsFoundException
	 */
	@GetMapping("/getDeploymentServerDetails")
	@ApiOperation(
			value = "Returns list of Component and URL's in response",
			notes = "Returns list ofComponent and URL's in response.",
			response = UrlDetailsDTO.class
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					})
	public ResponseEntity<UrlDetailsDTO> getUrl();
	
	
	
	/**
	 * to delete Component and It's URL details 
	 * @return Response of successful/error for delete operation
	 * @throws InvalidDataException
	 */
	@DeleteMapping("/deleteURL/{componentname}")
	@ApiOperation(
			value = "To Delete Component and URL.",
			notes = "Returns a sucess message after deletion."
			
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					})
	public void deleteUrl(@PathVariable (value = "componentname" ,required=true) @NotBlank String componentname);
	

	/**
	 * to Update Component and It's URL details 
	 * @return Response of updated Component details
	 * 
	 */
	@PutMapping("/updateURL")
	@ApiOperation(
			value = "To update URL Details in Component",
			notes = "Returns All URL's in response.",
			response = UrlDetailsDTO.class
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					})
	public ResponseEntity<UrlDetailsDTO> updateUrl( @Valid @RequestBody UrlDetailsDTO urldtl);
}
