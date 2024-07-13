package com.ar.apimovies;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.ar.apimovies.dto.SigninRequest;
import com.ar.apimovies.dto.SigninResponse;
import com.ar.apimovies.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/signin")
public class SigninServlet extends HttpServlet {
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

    SigninRequest signinDto = objectMapper.readValue(req.getInputStream(), SigninRequest.class);
    User user = userDao.signWithEmailAndPassword(signinDto);

    if (user != null) {
      SigninResponse signinResponse = new SigninResponse(user.getUserId(), user.getFirstName(), user.getLastName(),
          user.getEmail(), user.getBirthdate(), user.getCountry());
      String jsonResponse = objectMapper.writeValueAsString(signinResponse);
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
