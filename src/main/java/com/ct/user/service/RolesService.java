package com.ct.user.service;

import java.util.List;
import java.util.Optional;

import com.ct.user.model.Role;

public interface RolesService {

	List<Role> getAllRoles();

	Optional<Role> getRole(int roleId);

}
