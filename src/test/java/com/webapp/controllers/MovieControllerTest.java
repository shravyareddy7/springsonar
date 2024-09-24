package com.webapp.controllers;

import com.webapp.entities.Movie;
import com.webapp.entities.Theatre;
import com.webapp.services.MovieService;
import com.webapp.services.TheatreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MovieControllerTest {

    @InjectMocks
    private MovieController movieController;

    @Mock
    private MovieService movieService;

    @Mock
    private TheatreService theatreService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDisplayMovies(){
        int theatreId = 1;
        Set<Movie> movies = new HashSet<>();
        movies.add(new Movie("Movie1", "Genre1", 100));
        movies.add(new Movie("Movie2", "Genre2", 120));

        Theatre theatre = new Theatre(theatreId,"Test theatre");

        when(movieService.getMoviesByTheatreId(theatreId)).thenReturn(movies);
        when(theatreService.getTheatreById(theatreId)).thenReturn(theatre);

        String viewName = movieController.displayMovies(theatreId, model);

        verify(movieService, times(1)).getMoviesByTheatreId(theatreId);
        verify(theatreService, times(1)).getTheatreById(theatreId);
        verify(model, times(1)).addAttribute("movies", movies);
        verify(model, times(1)).addAttribute("theatre", theatre);

        assertEquals("movies", viewName);
        assertEquals(2, movies.size());
    }

    @Test
    void testShowAddMovieForm() {
        int theatreId = 1;

        String view = movieController.showAddMovieForm(theatreId, model);

        verify(model).addAttribute(eq("movie"), any(Movie.class));
        verify(model).addAttribute("theatreId", theatreId);
        assertEquals("movie-form", view);
    }

    @Test
    void testSaveMovie_CorrectTheatreAssociation() {
        int theatreId = 1;
        Movie movie = new Movie("Movie1", "Genre1", 100);
        Theatre theatre = new Theatre();
        theatre.setId(theatreId);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(theatreService.getTheatreById(theatreId)).thenReturn(theatre);

        String viewName = movieController.saveMovie(theatreId, movie, bindingResult, null, model);

        verify(movieService, times(1)).saveMovie(movie);
        assertEquals(theatre, movie.getTheatre());
        assertEquals("redirect:/theatres/1/movies", viewName);
    }
    @Test
    void testSaveMovie_ValidationError_MovieName() {
        int theatreId = 1;
        Movie movie = new Movie("", "Genre1", 100);

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = movieController.saveMovie(theatreId, movie, bindingResult, null, model);

        verify(movieService, never()).saveMovie(movie);
        assertEquals("movie-form", viewName);
        verify(model, times(1)).addAttribute("theatreId", theatreId);
    }

    @Test
    void testSaveMovie_ValidationError_MovieGenre() {
        int theatreId = 1;
        Movie movie = new Movie("Movie1", "", 100);

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = movieController.saveMovie(theatreId, movie, bindingResult, null, model);

        verify(movieService, never()).saveMovie(movie);
        assertEquals("movie-form", viewName);
        verify(model, times(1)).addAttribute("theatreId", theatreId);
    }

    @Test
    void testSaveMovie_TicketPriceValidation() {
        int theatreId = 1;
        Movie movie = new Movie("Movie1", "Genre1", 0.0);

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = movieController.saveMovie(theatreId, movie, bindingResult, null, model);

        verify(movieService, never()).saveMovie(movie);
        assertEquals("movie-form", viewName);
        verify(model, times(1)).addAttribute("theatreId", theatreId);
    }

    @Test
    void testDeleteMovie() {
        int theatreId = 1;
        int movieId = 100;

        String viewName = movieController.deleteMovie(theatreId, movieId);

        verify(movieService, times(1)).deleteMovieById(movieId);
        assertEquals("redirect:/theatres/1/movies", viewName);
    }

    @Test
    void testUpdateMovieForm_Success() {
        int theatreId = 1;
        int movieId = 10;
        Movie movie = new Movie("Movie1", "Genre1", 100);
        when(movieService.getMovieById(movieId)).thenReturn(movie);

        String viewName = movieController.updateMovieForm(theatreId, movieId, model);

        verify(movieService, times(1)).getMovieById(movieId);
        verify(model, times(1)).addAttribute("movie", movie);
        verify(model, times(1)).addAttribute("theatreId", theatreId);
        assertEquals("movie-form", viewName);
    }

}
