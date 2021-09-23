package com.ct.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ct.user.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
