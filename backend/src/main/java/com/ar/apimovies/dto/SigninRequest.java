package com.ar.apimovies.dto;

public class SigninRequest {

  private String email;
  private String password;

  public SigninRequest() {
  }

  public SigninRequest(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

}
