package com.mudynamics.metadataservice.controllers;



import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mudynamics.metadataservice.dto.ContextMenuDTO;
import com.mudynamics.metadataservice.dto.DetailsDTO;
import com.mudynamics.metadataservice.dto.PalletAndContextMenuDTO;
import com.mudynamics.metadataservice.exceptions.CustomException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Rest Service method details 
 * @author SJ00495527
 *
 */

@Api(value = "ContextMenuController", tags="")
@RequestMapping(value="/mudynamics/metadata/v1",produces="application/json")
public interface ContextMenuController {

	// ==================== Context Menu =========================
	
	
	/**
	 * Get All context Menu By pallet Icon label
	 * @return ContextMenuDTO
	 */
	@GetMapping("/getContextMenuByPalletIconLabel/{palletIconLabel}")
	@ApiOperation(
			value = "Respond List of all Context Menus associated with requested Pallet Icon",
			notes = "Returns a JSON Array with All Context Menus requested for Pallet Icon",
			response = ContextMenuDTO.class
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					})
	public ResponseEntity<ContextMenuDTO> getAllContextMenusByPalletIconLabel(@PathVariable (value = "palletIconLabel") String palletIconLabel)throws CustomException;
	
	
	
	
	@PutMapping("/updateContextMenuByPalletIconLabel/{palletIconLabel}")
	@ApiOperation(
			value = "Respond with updated Context Menus",
			notes = "Returns a JSON object with updated Context Menus",
			response = ContextMenuDTO.class
	)
	@ApiResponses(
			value = {
					@ApiResponse(code = 404, message = "Service not available"),
					@ApiResponse(code = 500, message = "Unexpected Runtime error")
					})
	public ResponseEntity<PalletAndContextMenuDTO> updateContextMenu(@PathVariable (value = "palletIconLabel") String palletIconLabel,
 @RequestBody ContextMenuDTO contextMenuDTO)throws CustomException;
	
	
	
	 @DeleteMapping("/deleteContextMenuDetailsByPalletIconLabel/{palletIconLabel}/{contextMenuDetailsLabel}")
		@ApiOperation(
				value = "Delete Context Menu Details Item status.",
				notes = "Returns a deletion status."
		)
		@ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
	 public ResponseEntity<String> deleteContextMenuDetailsByPalletIconLabel(@PathVariable (value = "palletIconLabel") String palletIconLabel,
			 @PathVariable (value = "contextMenuDetailsLabel") String contextMenuDetailsLabel)throws CustomException;


	 @GetMapping("/getAllcontextMenus")
		@ApiOperation(
				value = "Respond All Context Menus",
				notes = "Returns a JSON object with All Context Menus"
		)
		@ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
		public ResponseEntity<Map<String,List<DetailsDTO>>> getAllContextMenus()throws CustomException;
				
		
}
