package com.lee.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.lee.demo.models.ERole;
import com.lee.demo.models.Role;


public interface RoleRepository extends CrudRepository<Role, Long> {
	
	Optional<Role> findByName(String name);
}
