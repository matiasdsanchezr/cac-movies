package com.ar.apimovies.model;

public class Movie {

  private Long movieId;
  private String title;
  private String description;
  private String genre;
  private String duration;
  private String year;
  private String poster;

  public Movie() {

  }

  public Movie(Long movieId, String title, String description, String genre, String duration, String year,
      String poster) {
    this.movieId = movieId;
    this.title = title;
    this.description = description;
    this.genre = genre;
    this.duration = duration;
    this.year = year;
    this.poster = poster;
  }

  public String getPoster() {
    return poster;
  }

  public void setPoster(String poster) {
    this.poster = poster;
  }

  public Long getMovieId() {
    return movieId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

}
