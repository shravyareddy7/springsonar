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
@RequestMapping("theatres")
public class TheatreController {

    private final TheatreService theatreService;

    // Constructor injection
    @Autowired
    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @GetMapping("")
    public String showTheatres(Model model)
    {
        List<Theatre> theatres=theatreService.getAllTheatres();
        model.addAttribute("theatres",theatres);
        return "theatre";
    }

    @GetMapping("/add-theatre")
    public String addTheatre(Model model)
    {
        model.addAttribute("theatre",new Theatre());
        return "theatre-form";
    }

    @PostMapping("/save-theatre")
    public String saveTheatre(@Valid @ModelAttribute("theatre") Theatre theatre, BindingResult result, Model model){
        if (result.hasErrors()) {
            // Handle validation errors
            model.addAttribute("theatre", theatre);
            return "theatre-form"; // Return the form view to show errors
        }
        try {
            theatreService.saveTheatre(theatre);
            return "redirect:/theatres";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "theatre-form";
        }
    }

    @GetMapping("/update-theatre")
    public String updateTheatre(@RequestParam("theatreId") int id, Model model) {
        Theatre theatre = theatreService.getTheatreById(id);
        model.addAttribute("theatre", theatre);
        return "theatre-form";
    }

    @GetMapping("/delete-theatre")
    public String deleteTheatre(@RequestParam("theatreId") int id) {
        theatreService.deleteTheatreById(id);
        return "redirect:/theatres";
    }


}
