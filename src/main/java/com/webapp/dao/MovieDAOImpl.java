package com.webapp.dao;

import com.webapp.entities.Movie;
import com.webapp.entities.Theatre;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class MovieDAOImpl implements MovieDAO{

    @Autowired
    public SessionFactory sessionFactory;

    @Override
    public Set<Movie> getMoviesByTheatreId(int id) {
        Session session=sessionFactory.getCurrentSession();
        Set<Movie> movies=session.get(Theatre.class,id).getMovies();
        return movies;
    }

    @Override
    public Movie getMovieByTheatreId(int id) {
        Session session=sessionFactory.getCurrentSession();
        return session.get(Movie.class,id);
    }

    @Override
    public void saveMovie(Movie movie) {
        Session session=sessionFactory.getCurrentSession();
        session.saveOrUpdate(movie);
    }

    @Override
    public void updateMovie(Movie movie) {
        Session session = sessionFactory.getCurrentSession();
        session.update(movie); // Explicit update
    }

    @Override
    public void deleteMovie(int id) {
        Session session = sessionFactory.getCurrentSession();
        Movie movie = session.get(Movie.class, id);
        if (movie != null) {
            session.delete(movie); // Deletes movie if it exists
        }
    }
    @Override
    public Movie getMovieById(int movieId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Movie.class, movieId);
    }

    @Override
    public void deleteMovieById(int movieId) {
        Session session=sessionFactory.getCurrentSession();
        Movie movie = session.get(Movie.class, movieId);
        movie.getTheatre().getMovies().remove(movie); // Retrieve the movie entity by ID
            session.delete(movie);
    }


}
