package com.ar.apimovies.dto;

import java.sql.Timestamp;

public class SignupRequest {
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private Timestamp birthdate;
  private String country;

  public String getFirstName() {
    return firstName;
  }

  public SignupRequest() {
  }

  public SignupRequest(String firstName, String lastName, String email, String password, Timestamp birthdate,
      String country) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.birthdate = birthdate;
    this.country = country;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public Timestamp getBirthdate() {
    return birthdate;
  }

  public String getCountry() {
    return country;
  }
}
