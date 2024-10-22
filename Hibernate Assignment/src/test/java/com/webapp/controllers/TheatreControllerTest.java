package com.webapp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.webapp.entities.Theatre;
import com.webapp.services.TheatreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.ArrayList;
import java.util.List;

class TheatreControllerTest {

    @InjectMocks
    private TheatreController theatreController;

    @Mock
    private TheatreService theatreService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTheatre_EmptyName() {
        Theatre theatre = new Theatre("", "Location 1", null);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldError("name")).thenReturn(new FieldError("theatre", "name", "Theatre name is required"));

        String viewName = theatreController.saveTheatre(theatre, bindingResult, model);

        assertEquals("theatre-form", viewName);
        verify(model, times(1)).addAttribute("theatre", theatre);
    }

    @Test
    void testSaveTheatre_EmptyLocation() {
        Theatre theatre = new Theatre("Theatre 1", "", null);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldError("location")).thenReturn(new FieldError("theatre", "location", "Theatre location is required"));

        String viewName = theatreController.saveTheatre(theatre, bindingResult, model);

        assertEquals("theatre-form", viewName);
        verify(model, times(1)).addAttribute("theatre", theatre);
    }

    @Test
    void testSaveTheatre_ValidInput() {
        Theatre theatre = new Theatre("Theatre 1", "Location 1", null);
        when(bindingResult.hasErrors()).thenReturn(false); // No errors

        String viewName = theatreController.saveTheatre(theatre, bindingResult, model);

        verify(theatreService, times(1)).saveTheatre(theatre);
        assertEquals("redirect:/theatres", viewName);
    }

    @Test
    void testSaveTheatre_NullTheatre() {
        Theatre theatre = null;
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = theatreController.saveTheatre(theatre, bindingResult, model);

        assertEquals("theatre-form", viewName);
        verify(model, times(1)).addAttribute("theatre", theatre);
    }

    @Test
    void testSaveTheatre_RuntimeException() {
        Theatre theatre = new Theatre("Theatre 1", "Location 1", null);
        when(bindingResult.hasErrors()).thenReturn(false); // No errors
        doThrow(new RuntimeException("Database error")).when(theatreService).saveTheatre(any());

        String viewName = theatreController.saveTheatre(theatre, bindingResult, model);

        verify(theatreService, times(1)).saveTheatre(theatre);
        assertEquals("theatre-form", viewName);
        verify(model, times(1)).addAttribute("errorMessage", "Database error");
    }

    @Test
    void testUpdateTheatre() {
        int theatreId = 1;
        Theatre theatre = new Theatre();
        when(theatreService.getTheatreById(theatreId)).thenReturn(theatre);

        String viewName = theatreController.updateTheatre(theatreId, model);

        assertEquals("theatre-form", viewName);
        verify(model, times(1)).addAttribute("theatre", theatre);
    }

    @Test
    void testDeleteTheatre() {
        int theatreId = 1;

        String viewName = theatreController.deleteTheatre(theatreId);

        verify(theatreService, times(1)).deleteTheatreById(theatreId);
        assertEquals("redirect:/theatres", viewName);
    }

    @Test
    void testShowTheatres() {
        List<Theatre> theatres = new ArrayList<>();
        theatres.add(new Theatre(1, "Theatre 1"));
        theatres.add(new Theatre(2, "Theatre 2"));

        when(theatreService.getAllTheatres()).thenReturn(theatres);

        String viewName = theatreController.showTheatres(model);

        verify(theatreService, times(1)).getAllTheatres();
        verify(model, times(1)).addAttribute("theatres", theatres);
        assertEquals("theatre", viewName);
    }

    @Test
    void testAddTheatre() {
        String viewName = theatreController.addTheatre(model);
        verify(model, times(1)).addAttribute(eq("theatre"), any(Theatre.class)); // Verify that a new Theatre object is added to the model
        assertEquals("theatre-form", viewName);
    }
}
