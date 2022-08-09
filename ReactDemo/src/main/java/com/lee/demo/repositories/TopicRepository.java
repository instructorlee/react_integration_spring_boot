package com.lee.demo.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lee.demo.models.Topic;

public interface TopicRepository extends CrudRepository<Topic, Long> {
    
    List<Topic> findAll();
}
