package com.webapp.services;

import com.webapp.dao.MovieDAO;
import com.webapp.entities.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieDAO movieDAO;
    @Override
    public Set<Movie> getMoviesByTheatreId(int id) {
        return movieDAO.getMoviesByTheatreId(id);
    }

    @Override
    public void saveMovie(Movie movie) {
        movieDAO.saveMovie(movie);
    }
    @Override
    public void updateMovie(Movie movie) {
        movieDAO.updateMovie(movie);
    }

    @Override
    public void deleteMovie(int id) {
        movieDAO.deleteMovie(id);
    }

    @Override
    public Movie getMovieByTheatreId(int id) {
        return movieDAO.getMovieByTheatreId(id);
    }
    @Override
    public Movie getMovieById(int movieId) {
        return movieDAO.getMovieById(movieId);
    }

    @Override
    public void deleteMovieById(int movieId) {
        movieDAO.deleteMovieById(movieId);
    }

}
