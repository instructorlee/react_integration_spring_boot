package com.lee.demo.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lee.demo.models.Topic;
import com.lee.demo.services.TopicService;
import com.lee.demo.models.User;
import com.lee.demo.services.UserService;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    TopicService service;

    @Autowired
    UserService userService;
    
    @PostMapping("/add")
    public ResponseEntity<Topic> add(
        @Valid @RequestBody Topic topic,
        BindingResult result) {

            if ( !result.hasErrors() ) {
                return ResponseEntity.status(201).body(this.service.create(topic));
            }
        return ResponseEntity.status(422).body(null);
    }

    @PostMapping("/update")
    public ResponseEntity<Topic> update(
        @Valid @RequestBody Topic topic,
        BindingResult result) {

            if ( !result.hasErrors() ) {
                return ResponseEntity.status(200).body(this.service.update(topic));
            }
            return ResponseEntity.status(422).body(null);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Topic> update(
        @PathVariable Long id
        ) {

            this.service.delete(id);
            return ResponseEntity.status(200).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topic> view(
        @PathVariable Long id
        ) {
            return ResponseEntity.status(200).body(this.service.retrieve(id));
    }

    @GetMapping("/my")
    public ResponseEntity<List<Topic>> myTopics(
        Principal principal
        ) {

            User user = userService.findByEmail(principal.getName());

            return ResponseEntity.status(200).body(user.getTopics());
    }
}
