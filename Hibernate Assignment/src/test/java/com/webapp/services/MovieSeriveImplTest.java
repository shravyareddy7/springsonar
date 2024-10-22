package com.webapp.services;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.webapp.dao.MovieDAO;
import com.webapp.entities.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

class MovieServiceImplTest {

    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieDAO movieDAO;

    private Movie movie1;
    private Movie movie2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movie1 = new Movie("Movie One", "Genre A", 10.0);
        movie2 = new Movie("Movie Two", "Genre B", 15.0);
    }

    @Test
    void testGetMoviesByTheatreId() {
        int theatreId = 1;
        Set<Movie> expectedMovies = new HashSet<>();
        expectedMovies.add(movie1);
        expectedMovies.add(movie2);
        when(movieDAO.getMoviesByTheatreId(theatreId)).thenReturn(expectedMovies);

        Set<Movie> actualMovies = movieService.getMoviesByTheatreId(theatreId);

        assertEquals(expectedMovies, actualMovies);
        assertEquals(2, actualMovies.size());
        assertTrue(actualMovies.stream().anyMatch(m -> m.getName().equals("Movie One")));
        assertTrue(actualMovies.stream().anyMatch(m -> m.getName().equals("Movie Two")));
        verify(movieDAO, times(1)).getMoviesByTheatreId(theatreId);
    }

    @Test
    void testSaveMultipleMovies() {
        Set<Movie> movies = new HashSet<>();
        when(movieDAO.getMoviesByTheatreId(anyInt())).thenReturn(movies);

        movieService.saveMovie(movie1);
        movies.add(movie1);

        assertEquals(1, movies.size());

        movieService.saveMovie(movie2);
        movies.add(movie2);

        assertEquals(2, movies.size());

        verify(movieDAO, times(1)).saveMovie(movie1);
        verify(movieDAO, times(1)).saveMovie(movie2);
    }

    @Test
    void testUpdateMovie() {
        int movieId = 1;
        when(movieDAO.getMovieById(movieId)).thenReturn(movie1);

        movieService.updateMovie(movie1);

        verify(movieDAO, times(1)).updateMovie(movie1);
        assertNotNull(movieService.getMovieById(movieId));
    }

    @Test
    void testDeleteMovie() {
        movieService.saveMovie(movie1);
        int movieId = movie1.getId();

        Set<Movie> movies = new HashSet<>();
        movies.add(movie1);
        when(movieDAO.getMoviesByTheatreId(anyInt())).thenReturn(movies);

        assertEquals(1, movies.size());

        movieService.deleteMovie(movieId);

        verify(movieDAO, times(1)).deleteMovie(movieId);

        movies.remove(movie1);

        assertEquals(0, movies.size());
    }


    @Test
    void testGetMovieByTheatreId() {
        int theatreId = 1;
        when(movieDAO.getMovieByTheatreId(theatreId)).thenReturn(movie1);

        Movie actualMovie = movieService.getMovieByTheatreId(theatreId);

        assertEquals(movie1, actualMovie);
        verify(movieDAO, times(1)).getMovieByTheatreId(theatreId);
    }

    @Test
    void testGetMovieById() {
        int movieId = 1;
        when(movieDAO.getMovieById(movieId)).thenReturn(movie1);

        Movie actualMovie = movieService.getMovieById(movieId);

        assertEquals(movie1, actualMovie);
        verify(movieDAO, times(1)).getMovieById(movieId);
    }

    @Test
    void testDeleteMovieById() {
        movieService.saveMovie(movie1);
        int movieId = movie1.getId();

        movieService.deleteMovieById(movieId);

        verify(movieDAO, times(1)).deleteMovieById(movieId);

        Set<Movie> movies = new HashSet<>();
        when(movieDAO.getMoviesByTheatreId(anyInt())).thenReturn(movies);

        assertEquals(0, movies.size());
    }
}
