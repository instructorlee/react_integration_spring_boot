package com.lee.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.lee.demo.models.User;


public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByUsername(String username);
	
	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
