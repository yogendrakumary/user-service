package com.ct.user.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ct.user.model.Role;
import com.ct.user.service.RolesService;

@RestController
@CrossOrigin(origins = "*")
//@RequestMapping("/users/api")
public class RolesController {

	@Autowired
	private RolesService rolesService;

	@GetMapping("/roles")
	public List<Role> all() {
		return rolesService.getAllRoles();
	}
	@GetMapping("/roles/{id}")
	public Optional<Role> getRole(@PathVariable int id) {
		return rolesService.getRole(id);
	}
}
