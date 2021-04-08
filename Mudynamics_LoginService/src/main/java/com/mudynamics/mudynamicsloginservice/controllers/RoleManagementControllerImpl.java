package com.mudynamics.mudynamicsloginservice.controllers;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mudynamics.mudynamicsloginservice.exceptions.ResourceNotFoundException;
import com.mudynamics.mudynamicsloginservice.exceptions.RoleDeletionNotAllowedException;
import com.mudynamics.mudynamicsloginservice.model.AvailableUsersDTO;
import com.mudynamics.mudynamicsloginservice.model.Role;
import com.mudynamics.mudynamicsloginservice.model.RoleDTO;
import com.mudynamics.mudynamicsloginservice.model.User;
import com.mudynamics.mudynamicsloginservice.repositories.RoleRepository;
import com.mudynamics.mudynamicsloginservice.repositories.UserRepository;

@RestController
public class RoleManagementControllerImpl implements RoleManagementController{

	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	EntityManager manager;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public Set<RoleDTO> getAllRoles() {
		return roleRepo.findAll().stream().map(entity-> mapper.map(entity, RoleDTO.class))
				.collect(Collectors.toSet());
		
	}
	
	@Transactional
	@Override
	public void deleteRole(@PathVariable String role) throws RoleDeletionNotAllowedException{
		Query q =manager.createQuery("SELECT e FROM User e JOIN  e.roles a where a.name = :name");
		q.setParameter("name", role);
		if(!q.getResultList().isEmpty())
			throw new RoleDeletionNotAllowedException("Role is assigned to other Users as well, so not able to delete.");
		
		roleRepo.deleteByName(role);
		
	}

	@Override
	public RoleDTO createRole(@Valid @RequestBody RoleDTO dto) {
		Role role = new Role();
		role.setName(dto.getName());
		return mapper.map(roleRepo.save(role), RoleDTO.class);
		
	}

	@Override
	public Set<AvailableUsersDTO> getAllUsersByRole(@PathVariable String role) {
		Set<AvailableUsersDTO> userDTOs = new HashSet<>();
		Role userRole  = roleRepo.findByName(role).orElseThrow(()-> new ResourceNotFoundException("given Role: "+role+" not found in database.."));
		Query query=manager.createNativeQuery("SELECT user_id FROM user_roles WHERE role_id= ?1");
		query.setParameter(1, userRole.getId());
		@SuppressWarnings("unchecked")
		List<BigInteger> userId=query.getResultList();
		for(BigInteger id:userId) {
			User user=userRepository.findById(id.longValue()).get();
			userDTOs.add(mapper.map(user,AvailableUsersDTO.class));
		}
		return userDTOs;
	}

}
