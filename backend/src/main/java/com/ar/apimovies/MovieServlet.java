package com.ar.apimovies;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.ar.apimovies.dto.MovieDeleteRequest;
import com.ar.apimovies.model.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/peliculas")
public class MovieServlet extends HttpServlet {

  private MovieDao movieDao = new MovieDao();

  private ObjectMapper objectMapper = new ObjectMapper();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    resp.setHeader("Access-Control-Allow-Origin", "*");
    resp.setHeader("Access-Control-Allow-Methods", "*");
    resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    resp.setCharacterEncoding("UTF-8");
    req.setCharacterEncoding("UTF-8");

    Movie movie = objectMapper.readValue(req.getInputStream(),
        Movie.class);
    System.out.println(movie.getPoster());
    Long id = movieDao.insertMovie(movie);

    if (id != null) {
      String jsonResponse = objectMapper.writeValueAsString(id);
      resp.setContentType("application/json");
      resp.getWriter().write(jsonResponse);
      resp.setStatus(HttpServletResponse.SC_CREATED);
    } else {
      Map<String, String> payload = Map.of("message", "No se pudo insertar la pelicula en la base de datos.");
      String jsonResponse = objectMapper.writeValueAsString(payload);
      resp.setContentType("application/json");
      resp.getWriter().write(jsonResponse);
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setHeader("Access-Control-Allow-Origin", "*");
    resp.setHeader("Access-Control-Allow-Methods", "*");
    resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    resp.setCharacterEncoding("UTF-8");
    req.setCharacterEncoding("UTF-8");

    try {
      List<Movie> peliculas = movieDao.getAllMovies();
      String jsonResp = objectMapper.writeValueAsString(peliculas);
      resp.setContentType("application/json");
      resp.getWriter().write(jsonResp);

    } catch (NumberFormatException e) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID invalido");
      e.printStackTrace();
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    resp.setHeader("Access-Control-Allow-Origin", "*");
    resp.setHeader("Access-Control-Allow-Methods", "*");
    resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    resp.setCharacterEncoding("UTF-8");
    req.setCharacterEncoding("UTF-8");

    MovieDeleteRequest movieDeleteDto = objectMapper.readValue(req.getInputStream(),
        MovieDeleteRequest.class);

    if (movieDao.deleteMovie(movieDeleteDto)) {
      String message = "Pelicula eliminada de la base de datos";
      String jsonResponse = objectMapper.writeValueAsString(message);
      resp.setContentType("application/json");
      resp.getWriter().write(jsonResponse);
      resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    } else {
      String message = "Pelicula no registrada en la base de datos";
      String jsonResponse = objectMapper.writeValueAsString(message);
      resp.setContentType("application/json");
      resp.getWriter().write(jsonResponse);
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
  }

}
