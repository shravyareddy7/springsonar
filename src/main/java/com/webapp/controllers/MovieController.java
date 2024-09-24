package com.webapp.controllers;

import com.webapp.entities.Movie;
import com.webapp.entities.Theatre;
import com.webapp.services.MovieService;
import com.webapp.services.TheatreService;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/theatres/{theatreId}")
public class MovieController {

    private static final String MOVIES_ATTRIBUTE = "movies";
    private static final String MOVIE_ATTRIBUTE = "movie";
    private static final String THEATRE_ID_ATTRIBUTE = "theatreId";
    private static final String THEATRE_ATTRIBUTE = "theatre";
    private static final String MOVIE_FORM_VIEW = "movie-form";
    private static final String MOVIES_VIEW = "movies";
    private static final String REDIRECT_MOVIES = "redirect:/theatres/";

     private final MovieService movieService;
    private final TheatreService theatreService;
     public MovieController(MovieService movieService, TheatreService theatreService) {
        this.movieService = movieService;
        this.theatreService = theatreService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Double.class, "ticketPrice", new CustomNumberEditor(Double.class, true));
    }

    @GetMapping("/movies")
    public String displayMovies(@PathVariable int theatreId, Model model) {
        Set<Movie> movies = movieService.getMoviesByTheatreId(theatreId);
        model.addAttribute(MOVIES_ATTRIBUTE, movies);
        model.addAttribute(THEATRE_ATTRIBUTE, theatreService.getTheatreById(theatreId));
        return MOVIES_VIEW;
    }

    @GetMapping("/add-movie")
    public String showAddMovieForm(@PathVariable int theatreId, Model model) {
        Movie movie = new Movie();
        model.addAttribute(MOVIE_ATTRIBUTE, movie);
        model.addAttribute(THEATRE_ID_ATTRIBUTE, theatreId);
        return MOVIE_FORM_VIEW;
    }

    @PostMapping("/save-movie")
    public String saveMovie(@PathVariable int theatreId, @Valid @ModelAttribute(MOVIE_ATTRIBUTE) Movie movie, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        Theatre theatre = theatreService.getTheatreById(theatreId);
        if (bindingResult.hasErrors()) {
            model.addAttribute(THEATRE_ID_ATTRIBUTE, theatreId);
            return MOVIE_FORM_VIEW;
        }
        movie.setTheatre(theatre);

        movieService.saveMovie(movie);
        return REDIRECT_MOVIES + theatreId + "/movies";
    }

    @GetMapping("/update-movie")
    public String updateMovieForm(@PathVariable int theatreId, @RequestParam("movieId") int movieId, Model model) {
        Movie movie = movieService.getMovieById(movieId);
        model.addAttribute(MOVIE_ATTRIBUTE, movie);
        model.addAttribute(THEATRE_ID_ATTRIBUTE, theatreId);
        return MOVIE_FORM_VIEW;
    }

    @GetMapping("/delete-movie")
    public String deleteMovie(@PathVariable int theatreId, @RequestParam("movieId") int movieId) {
        movieService.deleteMovieById(movieId);
    
        return REDIRECT_MOVIES + theatreId + "/movies";
    }
}
