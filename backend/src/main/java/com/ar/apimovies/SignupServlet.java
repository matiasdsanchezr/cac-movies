package com.ar.apimovies;

import java.io.IOException;
import java.sql.Timestamp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.ar.apimovies.dto.SignupRequest;
import com.ar.apimovies.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
  private UserDao userDao = new UserDao();
  private ObjectMapper objectMapper = new ObjectMapper();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    resp.setHeader("Access-Control-Allow-Origin", "*");
    resp.setHeader("Access-Control-Allow-Methods", "*");
    resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    resp.setCharacterEncoding("UTF-8");
    req.setCharacterEncoding("UTF-8");

    SignupRequest signupQueryParam = objectMapper.readValue(req.getInputStream(), SignupRequest.class);

    String email = signupQueryParam.getEmail();
    User existing_user = userDao.getUserByEmail(email);
    System.out.println(existing_user);
    if (existing_user != null) {
      String jsonResponse = objectMapper.writeValueAsString("duplicated email");
      resp.setContentType("application/json");
      resp.getWriter().write(jsonResponse);
      resp.setStatus(HttpServletResponse.SC_CONFLICT);
      return;
    }

    String firstName = signupQueryParam.getFirstName();
    String lastname = signupQueryParam.getLastName();
    String password = signupQueryParam.getPassword();
    Timestamp birthdate = signupQueryParam.getBirthdate();
    String country = signupQueryParam.getCountry();
    User newUser = new User(0L, firstName, lastname, email, password, birthdate, country);

    Long id = userDao.addUser(newUser);
    if (id != null) {
      String jsonResponse = objectMapper.writeValueAsString(id);
      resp.setContentType("application/json");
      resp.getWriter().write(jsonResponse);
      resp.setStatus(HttpServletResponse.SC_CREATED);
    } else {
      String jsonResponse = objectMapper.writeValueAsString("error");
      resp.setContentType("application/json");
      resp.getWriter().write(jsonResponse);
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

  }

}
