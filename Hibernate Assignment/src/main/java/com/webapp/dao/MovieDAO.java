package com.webapp.dao;

import com.webapp.entities.Movie;
import java.util.Set;

public interface MovieDAO {
    public Set<Movie> getMoviesByTheatreId(int id);
    public Movie getMovieByTheatreId(int id);
    public void saveMovie(Movie movie);
    public void updateMovie(Movie movie);
    public void deleteMovie(int id);
    public Movie getMovieById(int movieId);
    public void deleteMovieById(int movieId);
}
