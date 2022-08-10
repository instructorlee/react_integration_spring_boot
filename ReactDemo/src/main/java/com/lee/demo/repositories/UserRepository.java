package com.lee.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.lee.demo.models.User;


public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByEmail(String email);
	
	Boolean existsByEmail(String email);
}
