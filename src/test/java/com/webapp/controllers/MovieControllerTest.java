package com.webapp.controllers;

import com.webapp.entities.Movie;
import com.webapp.entities.Theatre;
import com.webapp.services.MovieService;
import com.webapp.services.TheatreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MovieControllerTest {

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

        verify(movieService, times(1)).getMoviesByTheatreId(theatreId); // movieService should be called once
        verify(theatreService, times(1)).getTheatreById(theatreId); // theatreService should be called once
        verify(model, times(1)).addAttribute("movies", movies); // model should have movies added
        verify(model, times(1)).addAttribute("theatre", theatre); // model should have theatre added

        assertEquals("movies", viewName);
        assertEquals(2, movies.size());
    }

    @Test
    void testShowAddMovieForm() {
        int theatreId = 1;

        String view = movieController.showAddMovieForm(theatreId, model);

        verify(model).addAttribute(eq("movie"), any(Movie.class)); // Check that a new Movie is added
        verify(model).addAttribute("theatreId", theatreId);
        assertEquals("movie-form", view);
    }

    @Test
    void testSaveMovie_CorrectTheatreAssociation() {
        // Arrange
        int theatreId = 1;
        Movie movie = new Movie("Movie1", "Genre1", 100);
        Theatre theatre = new Theatre();
        theatre.setId(theatreId);

        // Mock behavior
        when(bindingResult.hasErrors()).thenReturn(false); // No validation errors
        when(theatreService.getTheatreById(theatreId)).thenReturn(theatre);

        // Act
        String viewName = movieController.saveMovie(theatreId, movie, bindingResult, null, model);

        // Assert
        verify(movieService, times(1)).saveMovie(movie); // Ensure movie was saved
        assertEquals(theatre, movie.getTheatre()); // Check if the movie is associated with the correct theatre
        assertEquals("redirect:/theatres/1/movies", viewName); // Check the redirected view
    }
    @Test
    void testSaveMovie_ValidationError_MovieName() {
        // Arrange
        int theatreId = 1;
        Movie movie = new Movie("", "Genre1", 100); // Invalid name (empty)

        // Mock behavior
        when(bindingResult.hasErrors()).thenReturn(true); // Simulate validation errors

        // Act
        String viewName = movieController.saveMovie(theatreId, movie, bindingResult, null, model);

        // Assert
        verify(movieService, never()).saveMovie(movie); // Ensure save was not called
        assertEquals("movie-form", viewName); // Check the returned view
        verify(model, times(1)).addAttribute("theatreId", theatreId); // Ensure theatre ID is added
    }

    @Test
    void testSaveMovie_ValidationError_MovieGenre() {
        // Arrange
        int theatreId = 1;
        Movie movie = new Movie("Movie1", "", 100); // Invalid genre (empty)

        // Mock behavior
        when(bindingResult.hasErrors()).thenReturn(true); // Simulate validation errors

        // Act
        String viewName = movieController.saveMovie(theatreId, movie, bindingResult, null, model);

        // Assert
        verify(movieService, never()).saveMovie(movie); // Ensure save was not called
        assertEquals("movie-form", viewName); // Check the returned view
        verify(model, times(1)).addAttribute("theatreId", theatreId); // Ensure theatre ID is added
    }

    @Test
    void testSaveMovie_TicketPriceValidation() {
        // Arrange
        int theatreId = 1;
        Movie movie = new Movie("Movie1", "Genre1", 0.0); // Invalid ticket price (0)

        // Mock behavior
        when(bindingResult.hasErrors()).thenReturn(true); // Simulate validation errors

        // Act
        String viewName = movieController.saveMovie(theatreId, movie, bindingResult, null, model);

        // Assert
        verify(movieService, never()).saveMovie(movie); // Ensure save was not called
        assertEquals("movie-form", viewName); // Check the returned view
        verify(model, times(1)).addAttribute("theatreId", theatreId); // Ensure theatre ID is added
    }

    @Test
    void testDeleteMovie_Success() {
        // Arrange
        int theatreId = 1;
        int movieId = 10;

        Movie movie = new Movie("Movie1", "Genre1", 100); // Mock movie object
        when(movieService.getMovieById(movieId)).thenReturn(movie); // Return the movie to delete
        doNothing().when(movieService).deleteMovieById(movieId); // Simulate deletion


        // Act
        String viewName = movieController.deleteMovie(theatreId, movieId);

        // Assert
        verify(movieService, times(1)).getMovieById(movieId); // Verify that getMovieById is called
        verify(movieService, times(1)).deleteMovieById(movieId); // Verify that deleteMovieById is called
        assertEquals("redirect:/theatres/" + theatreId + "/movies", viewName); // Check redirection URL
    }    

}
