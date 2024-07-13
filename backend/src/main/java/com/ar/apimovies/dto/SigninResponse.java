package com.ar.apimovies.dto;

import java.sql.Timestamp;

public class SigninResponse {
  private Long userId;
  private String firstName;
  private String lastName;
  private String email;
  private Timestamp birthdate;
  private String country;

  public SigninResponse(Long userId, String firstName, String lastName, String email, Timestamp birthdate,
      String country) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.birthdate = birthdate;
    this.country = country;
  }

  public Long getUserId() {
    return userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public Timestamp getBirthdate() {
    return birthdate;
  }

  public String getCountry() {
    return country;
  }

}
