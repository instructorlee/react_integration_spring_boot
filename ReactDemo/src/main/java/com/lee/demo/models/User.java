package com.lee.demo.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;

@Entity
@NoArgsConstructor
@Data
@Table(name = "users", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "email") 
    })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch = FetchType.LAZY) // mappedBy must match attribute in child
  private List<Topic> topics;

  @OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch = FetchType.LAZY) // mappedBy must match attribute in child
  private List<Joke> jokes;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "user_roles", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }

}
