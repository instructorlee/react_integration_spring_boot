package com.lee.demo.payload.response;

import com.lee.demo.models.User;

import lombok.Data;

@Data
public class JwtResponse {
  private String auth_token;
  private String type = "Bearer";
  private User user;

  public JwtResponse(String accessToken, User user) {
    this.auth_token = accessToken;
    this.user = user;
  }

  public String getauth_token() { // odd naming convention to match React requirements
    return auth_token;
  }

  public void setauth_token(String accessToken) { 
    this.auth_token = accessToken;
  }

}
