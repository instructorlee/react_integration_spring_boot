package com.lee.demo.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;

@Entity
@NoArgsConstructor
@Data
@Table(name="topics")
public class Topic {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Size(min=1)
    private String title;
    private String players;
    private String setting;
    private String plot;
    private String conflict;
    private String theme;
    private String narrative_arc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id") // parent-name_id
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy="topic", cascade=CascadeType.ALL, fetch = FetchType.LAZY) // mappedBy must match attribute in child
    private List<Joke> jokes;

}
