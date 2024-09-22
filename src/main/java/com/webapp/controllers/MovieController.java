package com.webapp.controllers;

import com.webapp.entities.Movie;
import com.webapp.entities.Theatre;
import com.webapp.services.MovieService;
import com.webapp.services.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/theatres/{theatreId}")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private TheatreService theatreService;

    // Display all movies by theatre ID
    @GetMapping("/movies")
    public String displayMovies(@PathVariable int theatreId, Model model) {
        Set<Movie> movies = movieService.getMoviesByTheatreId(theatreId);
        model.addAttribute("movies", movies);
        model.addAttribute("theatre", theatreService.getTheatreById(theatreId));
        return "movies"; // Return view that displays the movies
    }

    // Show the form for adding a new movie
    @GetMapping("/add-movie")
    public String showAddMovieForm(@PathVariable int theatreId, Model model) {
        Movie movie = new Movie();
        model.addAttribute("movie", movie);
        model.addAttribute("theatreId", theatreId); // Pass the theatre ID to the form
        return "movie-form"; // Return view for the movie form
    }

    // Save the movie
    @PostMapping("/save-movie")
    public String saveMovie(@PathVariable int theatreId, @Valid @ModelAttribute("movie") Movie movie, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        // Get the associated theatre by ID
        Theatre theatre = theatreService.getTheatreById(theatreId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("theatreId", theatreId); // Pass the theatre ID to the model
            return "movie-form"; // Return to the form with validation errors
        }
        movie.setTheatre(theatre);

        movieService.saveMovie(movie);
        return "redirect:/theatres/" + theatreId + "/movies";
    }

    @GetMapping("/update-movie")
    public String updateMovieForm(@PathVariable int theatreId, @RequestParam("movieId") int movieId, Model model) {
        Movie movie = movieService.getMovieById(movieId); // Fetch movie by ID
        model.addAttribute("movie", movie);
        model.addAttribute("theatreId", theatreId);
        return "movie-form"; 
    }

    @GetMapping("/delete-movie")
    public String deleteMovie(@PathVariable int theatreId, @RequestParam("movieId") int movieId) {
        Movie movie = movieService.getMovieById(movieId);
            movieService.deleteMovieById(movieId);
        return "redirect:/theatres/" + theatreId + "/movies"; // Redirect to the movies list after deletion
    }


}
