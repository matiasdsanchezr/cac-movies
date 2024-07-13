package com.ar.apimovies;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class CorsFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpResquest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    httpResponse.setHeader("Access-Control-Allow-Origin", "*");

    httpResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");

    httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization");

    httpResponse.setHeader("Access-Control-Max-Age", "600");

    if ("OPTIONS".equalsIgnoreCase(httpResquest.getMethod())) {
      httpResponse.setStatus(HttpServletResponse.SC_OK);// 200(ok)

    }

    chain.doFilter(request, response);
  }

}
