package com.ar.apimovies;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.ar.apimovies.dto.MovieDeleteRequest;
import com.ar.apimovies.model.Movie;
import java.sql.PreparedStatement;

public class MovieDao {

  public Long insertMovie(Movie pelicula) {
    DatabaseConnection databaseConn = new DatabaseConnection();
    Connection conn = databaseConn.conectar();
    PreparedStatement ps = null;
    ResultSet rs = null;

    String insertarPeliculaSql = "INSERT INTO movie(title,description,genre,duration,year,poster) VALUES(?,?,?,?,?,?)";

    try {
      ps = conn.prepareStatement(insertarPeliculaSql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, pelicula.getTitle());
      ps.setString(2, pelicula.getDescription());
      ps.setString(3, pelicula.getGenre());
      ps.setString(4, pelicula.getDuration());
      ps.setString(5, pelicula.getYear());
      ps.setString(6, pelicula.getPoster());

      int result = ps.executeUpdate();
      if (result > 0) {
        rs = ps.getGeneratedKeys();
        if (rs.next()) {
          return rs.getLong(1);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DbUtils.closeQuietly(rs);
      DbUtils.closeQuietly(ps);
      DbUtils.closeQuietly(conn);
    }

    return null;
  }

  public List<Movie> getAllMovies() {
    DatabaseConnection databaseConn = new DatabaseConnection();
    Connection conn = databaseConn.conectar();
    PreparedStatement ps = null;
    ResultSet rs = null;

    String SelectMoviesSql = "SELECT * FROM movie";
    List<Movie> movies = new ArrayList<>();

    try {
      ps = conn.prepareStatement(SelectMoviesSql);
      rs = ps.executeQuery();
      while (rs.next()) {
        Long movieId = rs.getLong("movie_id");
        String title = rs.getString("title");
        String description = rs.getString("description");
        String genre = rs.getString("genre");
        String duration = rs.getString("duration");
        String year = rs.getString("year");
        String poster = rs.getString("poster");
        Movie movie = new Movie(movieId, title, description, genre, duration, year, poster);
        movies.add(movie);
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DbUtils.closeQuietly(rs);
      DbUtils.closeQuietly(ps);
      DbUtils.closeQuietly(conn);
    }

    return movies;
  }

  public boolean deleteMovie(MovieDeleteRequest movieDeleteDto) {
    DatabaseConnection databaseConn = new DatabaseConnection();
    Connection conn = databaseConn.conectar();
    PreparedStatement ps = null;

    String DeleteMovieSql = "DELETE FROM movie WHERE movie_id = ?";

    try {
      ps = conn.prepareStatement(DeleteMovieSql);
      ps.setLong(1, movieDeleteDto.getMovieId());
      if (ps.executeUpdate() > 0)
        return true;
      else
        return false;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DbUtils.closeQuietly(ps);
      DbUtils.closeQuietly(conn);
    }

    return false;
  }

}
