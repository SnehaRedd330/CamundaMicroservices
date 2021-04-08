
package com.mudynamics.metadataservice.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mudynamics.metadataservice.dto.TemplateDetailsDTO;
import com.mudynamics.metadataservice.exceptions.CustomException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "TemplateDetailsController",tags="")
@RequestMapping(value="/mudynamics/metadata/v1",produces="application/json")
public interface TemplateDetailsController {


	
	@DeleteMapping(path="/deleteTemplateDetails/{palletIconLabel}/{templateName}",produces="application/json")
	@ApiOperation(
			value = "Respond template deletion success Details",
			notes = "Returns template deletion Success message"
			
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					})
	public ResponseEntity<String> deleteTemplate(@PathVariable (value = "palletIconLabel") String palletIconLabel,
			 @PathVariable (value = "templateName") String templateName);
	
	
	
	
	@GetMapping("/getTemplatesByPalletIconLabel/{palletIconLabel}")
	@ApiOperation(
			value = "Respond template Details.",
			notes = "Returns template details.",
			response = TemplateDetailsDTO.class
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					})
	public ResponseEntity<List<TemplateDetailsDTO>> getAllTemplatesByPalletIconLabel(@PathVariable (value = "palletIconLabel") String palletIconLabel)throws CustomException;
	
	
	 @PutMapping("/updateTemplateDetails")
	 @ApiOperation(
				value = "Respond updated template Details.",
				notes = "Returns updated template details.",
				response = TemplateDetailsDTO.class
		)
		@ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
	 public ResponseEntity<TemplateDetailsDTO> updateTemplate(@Valid @RequestBody TemplateDetailsDTO details);

	 
	 	@GetMapping("/getAllTemplates")
		@ApiOperation(
				value = "Respond all templates Details.",
				notes = "Returns templates list.",
				response = TemplateDetailsDTO.class
		)
		@ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
		public   ResponseEntity<List<TemplateDetailsDTO>> getAllTemplates()throws CustomException;
		
}
