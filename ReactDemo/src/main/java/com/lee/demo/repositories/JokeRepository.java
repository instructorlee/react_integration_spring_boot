package com.lee.demo.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lee.demo.models.Joke;

public interface JokeRepository extends CrudRepository<Joke, Long> {
    
    List<Joke> findAll();
}
