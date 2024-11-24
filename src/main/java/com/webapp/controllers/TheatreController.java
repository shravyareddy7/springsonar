package com.webapp.controllers;

import com.webapp.entities.Theatre;
import com.webapp.services.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(TheatreController.THEATRES_BASE_URL)
public class TheatreController {

    // Constant declarations
    public static final String THEATRES_BASE_URL = "theatres";
    public static final String THEATRE_ATTRIBUTE = "theatre";
    public static final String THEATRE_LIST_VIEW = "theatre";
    public static final String THEATRE_FORM_VIEW = "theatre-form";
    public static final String REDIRECT_THEATRES = "redirect:/theatres";
    public static final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";

    private final TheatreService theatreService;

    // Constructor injection
    @Autowired
    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @GetMapping("")
    public String showTheatres(Model model) {
        List<Theatre> theatres = theatreService.getAllTheatres();
        model.addAttribute(THEATRE_ATTRIBUTE + "s", theatres); // Add the list of theatres to the model
        return THEATRE_LIST_VIEW; // Return the view that displays the list of theatres
    }

    @GetMapping("/add-theatre")
    public String addTheatre(Model model) {
        model.addAttribute(THEATRE_ATTRIBUTE, new Theatre()); // Add a new Theatre object to the model
        return THEATRE_FORM_VIEW; // Return the form view for adding a new theatre
    }

    @PostMapping("/save-theatre")
    public String saveTheatre(@Valid @ModelAttribute(THEATRE_ATTRIBUTE) Theatre theatre, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Handle validation errors
            model.addAttribute(THEATRE_ATTRIBUTE, theatre);
            return THEATRE_FORM_VIEW; // Return the form view with validation errors
        }
        try {
            theatreService.saveTheatre(theatre); // Save the theatre entity
            return REDIRECT_THEATRES; // Redirect to the list of theatres
        } catch (RuntimeException e) {
            model.addAttribute(ERROR_MESSAGE_ATTRIBUTE, e.getMessage());
            return THEATRE_FORM_VIEW; // Return the form view with an error message
        }
    }

    @GetMapping("/update-theatre")
    public String updateTheatre(@RequestParam("theatreId") int id, Model model) {
        Theatre theatre = theatreService.getTheatreById(id); // Get the theatre by ID
        model.addAttribute(THEATRE_ATTRIBUTE, theatre); // Add the theatre to the model
        return THEATRE_FORM_VIEW; // Return the form view for updating the theatre
    }

    @GetMapping("/delete-theatre")
    public String deleteTheatre(@RequestParam("theatreId") int id) {
        theatreService.deleteTheatreById(id); // Delete the theatre by ID
        return REDIRECT_THEATRES; // Redirect to the list of theatres
    }
}
