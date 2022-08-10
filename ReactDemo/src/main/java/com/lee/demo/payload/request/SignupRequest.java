package com.lee.demo.payload.request;

import java.util.Set;

import javax.validation.constraints.*;

import lombok.Data;

@Data
public class SignupRequest {
  
  private String email;

  private Set<String> role;

  
  private String password;

  
  private String confirm_password;
}
