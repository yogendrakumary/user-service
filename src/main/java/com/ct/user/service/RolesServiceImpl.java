package com.ct.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.user.model.Role;
import com.ct.user.repo.RoleRepository;

import lombok.extern.java.Log;

@Service
@Log
public class RolesServiceImpl implements RolesService {

	@Autowired
	private RoleRepository repository;

	@Override
	public List<Role> getAllRoles() {
		log.info("Inside getAllRoles");
		return repository.findAll();
	}

	@Override
	public Optional<Role> getRole(int roleId) {
		return repository.findById(roleId);
	}
}
