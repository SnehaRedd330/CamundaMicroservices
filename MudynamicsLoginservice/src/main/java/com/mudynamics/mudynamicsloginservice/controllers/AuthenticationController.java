package com.mudynamics.mudynamicsloginservice.controllers;

import javax.validation.Valid;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mudynamics.mudynamicsloginservice.exceptions.DuplicateUserNameException;
import com.mudynamics.mudynamicsloginservice.payload.JwtAuthenticationResponse;
import com.mudynamics.mudynamicsloginservice.payload.LoginRequest;
import com.mudynamics.mudynamicsloginservice.payload.SignUpRequest;
import com.mudynamics.mudynamicsloginservice.payload.SignUpResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RefreshScope
@Api(value = "AuthenticationController", tags="")
@RequestMapping(value="/mudynamics/api/auth",produces="application/json")
public interface AuthenticationController {

	
	
	    @PostMapping("/signin")
		@ApiOperation(
				value = "Respond authenticated tocken",
				notes = "Returns a authenticated tocken",
				response= JwtAuthenticationResponse.class
		)
		@ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
	    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest);
	    
	    
	    @PostMapping("/signup")
	    @ApiOperation(
				value = "Respond New created USER Details",
				notes = "Returns a JSON object New created USER Details",
				response= SignUpResponse.class
		)
		@ApiResponses(
				value = {
						@ApiResponse(code = 404, message = "Service not available"),
						@ApiResponse(code = 500, message = "Unexpected Runtime error")
						})
	    public ResponseEntity<SignUpResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest)throws DuplicateUserNameException;
}
