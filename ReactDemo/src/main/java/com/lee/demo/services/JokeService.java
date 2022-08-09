package com.lee.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lee.demo.models.Joke;
import com.lee.demo.repositories.JokeRepository;

@Service
public class JokeService
 {
    @Autowired
	private JokeRepository repository;
	
	public List<Joke> all() {
		return this.repository.findAll();
	}
	
	public Joke create(Joke Joke) {
		
		return this.repository.save(Joke);
	}
	
	public void delete(Long id) {
		this.repository.deleteById(id);
	}
	
	public Joke retrieve(Long id) {
		return this.repository.findById(id).get();
	}
	
	
	public Joke update(Joke Joke) {
		return this.repository.save(Joke);
	}
}
