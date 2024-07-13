package com.ar.apimovies.model;

import java.sql.Timestamp;

public class User {
  private Long userId;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private Timestamp birthdate;
  private String country;

  public User() {

  }

  public User(Long userId, String firstName, String lastName, String email, String password, Timestamp birthdate,
      String country) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.birthdate = birthdate;
    this.country = country;
  }

  @Override
  public String toString() {
    return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Timestamp getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(Timestamp birthdate) {
    this.birthdate = birthdate;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

}
