package com.ar.apimovies.dto;

public class MovieDeleteRequest {
  long movieId;

  public MovieDeleteRequest() {
  }

  public MovieDeleteRequest(long movieId) {
    this.movieId = movieId;
  }

  public long getMovieId() {
    return movieId;
  }

}
