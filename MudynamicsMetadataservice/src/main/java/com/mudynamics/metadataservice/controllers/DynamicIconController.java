package com.mudynamics.metadataservice.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mudynamics.metadataservice.dto.AllMetadataDTO;
import com.mudynamics.metadataservice.dto.PalletIconDTO;
import com.mudynamics.metadataservice.exceptions.CustomException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "DynamicIconController",tags="")
@RequestMapping(value="/mudynamics/metadata/v1",produces="application/json")
public interface DynamicIconController {

	
		
	@DeleteMapping(path="/deleteDynamicIcon/{label}",produces="text/plain")
	@ApiOperation(
			value = "Respond dynamic icon deletion success Details",
			notes = "Returns Success message"
			
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					})
	public ResponseEntity<String> deleteDynamicIconByLabel(@PathVariable String label)throws CustomException;
	
	
	
	
	@GetMapping("/getPaletteIconImageData")
	@ApiOperation(
			value = "Respond dynamic icon creation success Details",
			notes = "Returns PalletIcon list",
			response = PalletIconDTO.class
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					})
	public ResponseEntity<List<PalletIconDTO>> getAllPaletteIconImageData()throws  CustomException;
	
	
	 @PutMapping("/updateDynamicIcon")
	 @ApiOperation(
				value = "Respond dynamic icon updation Details",
				notes = "Returns updated PalletIcon",
				response = PalletIconDTO.class
		)
		@ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
	 public ResponseEntity<PalletIconDTO> updateDynamicIcon(@Valid @RequestBody PalletIconDTO dto);

	
	 @GetMapping("/getAllMetaData")
		@ApiOperation(
				value = "Respond all metadata.",
				notes = "Returns all metadata."
				
		)
		@ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						}) 
	 public ResponseEntity<Map<String, Object>> getAllMetada()throws  CustomException;
	 
	 
	 @PostMapping(path="/postAllMetadata")
		@ApiOperation(
				value = "Respond  success Details of all posted metadata",
				notes = "Returns Successfully posted metadata details",
				response= AllMetadataDTO.class
		)
		@ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
		public  ResponseEntity<AllMetadataDTO> postAllMetadata( @RequestBody AllMetadataDTO metadataDTO)throws CustomException;
		
}
