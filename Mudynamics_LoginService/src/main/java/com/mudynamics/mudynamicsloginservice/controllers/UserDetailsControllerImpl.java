package com.mudynamics.mudynamicsloginservice.controllers;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mudynamics.mudynamicsloginservice.exceptions.ResourceNotFoundException;
import com.mudynamics.mudynamicsloginservice.model.ProcessCategory;
import com.mudynamics.mudynamicsloginservice.model.Role;
import com.mudynamics.mudynamicsloginservice.model.User;
import com.mudynamics.mudynamicsloginservice.model.UserDTO;
import com.mudynamics.mudynamicsloginservice.payload.UserIdentityAvailability;
import com.mudynamics.mudynamicsloginservice.payload.UserProfile;
import com.mudynamics.mudynamicsloginservice.repositories.ProcessCategoryRepository;
import com.mudynamics.mudynamicsloginservice.repositories.RoleRepository;
import com.mudynamics.mudynamicsloginservice.repositories.UserRepository;


@RestController
public class UserDetailsControllerImpl implements UserDetailsController {
	
	   @Autowired
	    private UserRepository userRepository;
	   @Autowired
	   private ModelMapper mapper;
	  
	   @Autowired
	   private ProcessCategoryRepository processCategoryRepository;
	   
	   @Autowired
	   private RoleRepository roleRepository;
	   private static Logger logger = Logger.getLogger(UserDetailsControllerImpl.class.getName());

	@Override
	public UserIdentityAvailability checkUsernameAvailability(@PathVariable String username) {
		  Boolean isAvailable = userRepository.existsByUsername(username);
	        return new UserIdentityAvailability(isAvailable);
	}

	@Override
	public UserIdentityAvailability checkEmailAvailability(@PathVariable String email) {
		Boolean isAvailable = userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
	}

	@Override
	public UserProfile getUserProfile(@PathVariable String username) {
		 User user = userRepository.findByUsername(username)
	                .orElseThrow(() -> new ResourceNotFoundException("requested user name:"+username+" not found in database."));


	        return new UserProfile( user.getUsername(), user.getName(), user.getCreatedAt(), user.getUpdatedAt());
	}

	@Override
	public List<UserDTO> getAllUsers() {
		
		return userRepository.findAll().stream().map(user-> mapper.map(user, UserDTO.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void deleteUser(@PathVariable String username) {
		userRepository.deleteByUsernameOrEmail(username, username);
		logger.log(Level.INFO,"user deleted successfully..");
	}

	@Override
	public UserDTO updateUserByRoleAndCategory(@RequestBody UserDTO dto) {
		User convertedUser =mapper.map(dto, User.class);
		User user=userRepository.findByUsernameOrEmail(dto.getUsername(), dto.getEmail()).orElseThrow(()-> new ResourceNotFoundException("requested user name:"+dto.getUsername()+" not found in database."));
		if(convertedUser.getRoles()!= null) {
		  user.getRoles().clear();
		
		convertedUser.getRoles()
		.forEach(role-> {
			Optional<Role> rl = roleRepository.findByName(role.getName());
			if(rl.isPresent()){
			user.getRoles().add(rl.get());
			
			userRepository.save(user);
			}
		});
		}
		if(convertedUser.getCategory()!= null) {
			user.getCategory().clear();
			convertedUser.getCategory()
			.forEach(category-> {
				ProcessCategory processCategory = processCategoryRepository.findByCategory(category.getCategory());
				user.getCategory().add(processCategory);
				userRepository.save(user);
			});
		}
		return dto;
	}

}
