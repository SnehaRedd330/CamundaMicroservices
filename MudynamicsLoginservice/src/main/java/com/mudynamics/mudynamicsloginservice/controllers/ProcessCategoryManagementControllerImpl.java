package com.mudynamics.mudynamicsloginservice.controllers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mudynamics.mudynamicsloginservice.exceptions.DuplicateResourceException;
import com.mudynamics.mudynamicsloginservice.exceptions.ProcessCategoryDeletionNotAllowedException;
import com.mudynamics.mudynamicsloginservice.exceptions.ResourceNotFoundException;
import com.mudynamics.mudynamicsloginservice.model.AvailableUsersDTO;
import com.mudynamics.mudynamicsloginservice.model.ProcessCategory;
import com.mudynamics.mudynamicsloginservice.model.ProcessCategoryDTO;
import com.mudynamics.mudynamicsloginservice.model.User;
import com.mudynamics.mudynamicsloginservice.repositories.ProcessCategoryRepository;
import com.mudynamics.mudynamicsloginservice.repositories.UserRepository;


@RestController
public class ProcessCategoryManagementControllerImpl implements ProcessCategoryManagementController {

	@Autowired
	private ProcessCategoryRepository categoryRepository;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private  ModelMapper mapper;
	
	@Autowired
	private EntityManager manager;
	@Override
	public ResponseEntity<List<String>> getAllWorkflowCategories(@RequestParam String userId) {
		List<String> categorys = new ArrayList<String>();
		if(userId.isEmpty())
		{
			categoryRepository.findAll().forEach(category-> {
				categorys.add(category.getCategory());
			});
		}
		else {
			User user=userRepository.findByUsername(userId).orElseThrow(()-> new ResourceNotFoundException("Username "+userId+" not found in database..."));
			Set<ProcessCategory> processCategories= user.getCategory();
			
			processCategories.forEach(category-> {
				categorys.add(category.getCategory());
			});
		}
		Collections.sort(categorys);
		return new ResponseEntity<>(categorys,HttpStatus.OK);
	}

	@Override
	@Transactional
	public void deleteProcessCategory(@PathVariable String userId,
			@PathVariable String processCategory) {
		int count=0;
		if(categoryRepository.existsByCategory(processCategory)) {
			ProcessCategory category = categoryRepository.findByCategory(processCategory);
			Query query=manager.createNativeQuery("SELECT count(*) FROM user_process_category WHERE process_category_id= ?1");
			query.setParameter(1, category.getId());
			count=((BigInteger) query.getSingleResult()).intValue();
			
			
			 if(count == 1) {
				User user =userRepository.findByUsername("ADMIN").get();
				if(user.getCategory().contains(category))
						user.getCategory().remove(category);
				--count;
			}
			 if(count==0) 
					categoryRepository.deleteById(category.getId());
			else throw new ProcessCategoryDeletionNotAllowedException("given Process Category: "+processCategory+" already mapped to other Users");

		}
		else throw new ResourceNotFoundException("given Process Category: "+processCategory+" not found in database..");
		
			}

	@Override
	public ResponseEntity<ProcessCategoryDTO> createProcessCategory(@PathVariable String userId,
			@Valid @RequestBody ProcessCategoryDTO dto) {
		
		if(!categoryRepository.existsByCategory(dto.getCategory())) {
			ProcessCategory category=mapper.map(dto,ProcessCategory.class);
			ProcessCategory processCategory=categoryRepository.save(category);
			User user =userRepository.findByUsername("ADMIN").get();
			user.getCategory().add(processCategory);
			userRepository.save(user);
			  ProcessCategoryDTO categoryDTO=mapper.map(processCategory, ProcessCategoryDTO.class);
			  return new ResponseEntity<>(categoryDTO,HttpStatus.CREATED);
		}
		else 
			throw new DuplicateResourceException("given Process Category:"+dto.getCategory()+" is already exists in database..");
		
	}

	@Override
	public ResponseEntity<Set<AvailableUsersDTO>> getAllUsersByCategory(
			@NotBlank(message = "category must not be blank") @PathVariable String category) {
		Set<AvailableUsersDTO> userDTOs = new HashSet<>();
		ProcessCategory processCategory=categoryRepository.findByCategory(category);
		Query query=manager.createNativeQuery("SELECT user_id FROM user_process_category WHERE process_category_id= ?1");
		query.setParameter(1, processCategory.getId());
		@SuppressWarnings("unchecked")
		List<BigInteger> userId=query.getResultList();
		for(BigInteger id:userId) {
			User user=userRepository.findById(id.longValue()).get();
			userDTOs.add(mapper.map(user,AvailableUsersDTO.class));
		}
		return new ResponseEntity<>(userDTOs,HttpStatus.OK);
	}

	

	
}
