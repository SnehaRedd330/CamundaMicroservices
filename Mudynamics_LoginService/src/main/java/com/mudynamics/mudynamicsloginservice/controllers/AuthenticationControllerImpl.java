package com.mudynamics.mudynamicsloginservice.controllers;



import static com.mudynamics.mudynamicsloginservice.config.Constants.TIME_FORMATTER;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mudynamics.mudynamicsloginservice.exceptions.DuplicateUserNameException;
import com.mudynamics.mudynamicsloginservice.exceptions.ResourceNotFoundException;
import com.mudynamics.mudynamicsloginservice.model.ProcessCategory;
import com.mudynamics.mudynamicsloginservice.model.Role;
import com.mudynamics.mudynamicsloginservice.model.User;
import com.mudynamics.mudynamicsloginservice.payload.JwtAuthenticationResponse;
import com.mudynamics.mudynamicsloginservice.payload.LoginRequest;
import com.mudynamics.mudynamicsloginservice.payload.SignUpRequest;
import com.mudynamics.mudynamicsloginservice.payload.SignUpResponse;
import com.mudynamics.mudynamicsloginservice.repositories.ProcessCategoryRepository;
import com.mudynamics.mudynamicsloginservice.repositories.RoleRepository;
import com.mudynamics.mudynamicsloginservice.repositories.UserRepository;
import com.mudynamics.mudynamicsloginservice.security.JwtTokenProvider;

import io.swagger.annotations.Api;

@RestController
@Api(value = "AuthenticationController", tags="")
public class AuthenticationControllerImpl implements AuthenticationController{

	  @Autowired
	    AuthenticationManager authenticationManager;

	    @Autowired
	    UserRepository userRepository;

	    @Autowired
	    RoleRepository roleRepository;

	    @Autowired
	    PasswordEncoder passwordEncoder;

	    @Autowired
	    JwtTokenProvider tokenProvider;
	    
	    @Autowired
	    ProcessCategoryRepository proCatRepo;
	    
	    @Autowired
	    ProcessCategoryRepository processCategoryRepository;
	   
	@Override
	public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest){
		 String jwt=null;
		 Collection<? extends GrantedAuthority> authorities=null;
		 Set<ProcessCategory> proCat=new HashSet<>();
		 Set<String> categories=new HashSet<>();
		 try{
		  Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        loginRequest.getUsernameOrEmail(),
	                        loginRequest.getPassword()
	                )
	        );
		
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        authorities =authentication.getAuthorities();
	        jwt = tokenProvider.generateToken(authentication);
	       userRepository.findByUsernameOrEmail( loginRequest.getUsernameOrEmail(), loginRequest.getUsernameOrEmail()).ifPresent(user->{
	        	user.setUpdatedAt(new SimpleDateFormat(TIME_FORMATTER).format(new Date()));
	        	proCat.addAll(user.getCategory());
	        	userRepository.save(user);
	        });
	      proCat.stream().forEach(category -> {
	    	  categories.add(category.getCategory());
	      });
		 }catch(BadCredentialsException ex){
			 throw new BadCredentialsException("Bad credentials provided..",ex);
		 }
	        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, loginRequest.getUsernameOrEmail(),authorities,categories));
	}

	@Override
	public ResponseEntity<SignUpResponse> registerUser(@RequestBody @Valid SignUpRequest signUpRequest) throws DuplicateUserNameException {
		Set<Role> roleSet= new HashSet<>();
		Set<ProcessCategory> catSet= new HashSet<>();
		if(userRepository.existsByUsername(signUpRequest.getUsername()))
	            throw new DuplicateUserNameException("Username is already taken!");
	        
	        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
	            throw new DuplicateUserNameException("Email Address already in use!");
	        }

	        // Creating user's account
	        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
	                signUpRequest.getEmail(), signUpRequest.getPassword());

	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        
	        Role userRole = roleRepository.findByName("ROLE_USER")
	                 .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
	        roleSet.add(userRole);
	        
	        user.setRoles(roleSet);
	        if(!proCatRepo.existsByCategory(signUpRequest.getCategory())) {
	        	User adminUser=userRepository.findByUsername("ADMIN").orElseThrow(()-> new ResourceNotFoundException("ADMIN User is not available in database.."));
	        ProcessCategory processCategory=proCatRepo.save(new ProcessCategory(signUpRequest.getCategory()));
	        adminUser.getCategory().add(processCategory);
	        userRepository.save(adminUser);
	        catSet.add(processCategory);
	        user.setCategory(catSet);
	        }else {
	        	 ProcessCategory processCategory=proCatRepo.findByCategory(signUpRequest.getCategory());
	        	   catSet.add(processCategory);
	   	        user.setCategory(catSet);
	        }
	        	
	       userRepository.save(user);

	      
	        return new ResponseEntity<>(new SignUpResponse(true, "User registered successfully"),HttpStatus.CREATED);
	      
	    }

}
