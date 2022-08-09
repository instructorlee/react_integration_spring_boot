package com.lee.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lee.demo.models.Topic;
import com.lee.demo.repositories.TopicRepository;

@Service
public class TopicService {
    
    @Autowired
	private TopicRepository repository;
	
	
	public List<Topic> all() {
		return this.repository.findAll();
	}
	
	public Topic create(Topic Topic) {
		
		return this.repository.save(Topic);
	}
	
	public void delete(Long id) {
		this.repository.deleteById(id);
	}
	
	public Topic retrieve(Long id) {
		return this.repository.findById(id).get();
	}
	
	
	public Topic update(Topic Topic) {
		return this.repository.save(Topic);
	}
}
