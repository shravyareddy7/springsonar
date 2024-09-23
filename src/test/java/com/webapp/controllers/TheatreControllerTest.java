package com.webapp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.webapp.controllers.TheatreController;
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
        // Arrange
        Theatre theatre = new Theatre("", "Location 1", null); // Invalid theatre name
        when(bindingResult.hasErrors()).thenReturn(true); // Simulate validation errors
        when(bindingResult.getFieldError("name")).thenReturn(new FieldError("theatre", "name", "Theatre name is required"));

        // Act
        String viewName = theatreController.saveTheatre(theatre, bindingResult, model);

        // Assert
        assertEquals("theatre-form", viewName); // Return to form view
        verify(model, times(1)).addAttribute("theatre", theatre); // Ensure theatre is passed to model
    }

    @Test
    void testSaveTheatre_EmptyLocation() {
        // Arrange
        Theatre theatre = new Theatre("Theatre 1", "", null); // Invalid theatre location
        when(bindingResult.hasErrors()).thenReturn(true); // Simulate validation errors
        when(bindingResult.getFieldError("location")).thenReturn(new FieldError("theatre", "location", "Theatre location is required"));

        // Act
        String viewName = theatreController.saveTheatre(theatre, bindingResult, model);

        // Assert
        assertEquals("theatre-form", viewName); // Return to form view
        verify(model, times(1)).addAttribute("theatre", theatre); // Ensure theatre is passed to model
    }

    @Test
    void testSaveTheatre_ValidInput() {
        // Arrange
        Theatre theatre = new Theatre("Theatre 1", "Location 1", null);
        when(bindingResult.hasErrors()).thenReturn(false); // No errors

        // Act
        String viewName = theatreController.saveTheatre(theatre, bindingResult, model);

        // Assert
        verify(theatreService, times(1)).saveTheatre(theatre); // Ensure save was called
        assertEquals("redirect:/theatres", viewName); // Check the redirect
    }

    @Test
    void testSaveTheatre_NullTheatre() {
        // Arrange
        Theatre theatre = null; // Invalid theatre
        when(bindingResult.hasErrors()).thenReturn(true); // Simulate validation errors

        // Act
        String viewName = theatreController.saveTheatre(theatre, bindingResult, model);

        // Assert
        assertEquals("theatre-form", viewName); // Return to form view
        verify(model, times(1)).addAttribute("theatre", theatre); // Ensure null is passed to model
    }

    @Test
    void testSaveTheatre_RuntimeException() {
        // Arrange
        Theatre theatre = new Theatre("Theatre 1", "Location 1", null);
        when(bindingResult.hasErrors()).thenReturn(false); // No errors
        doThrow(new RuntimeException("Database error")).when(theatreService).saveTheatre(any());

        // Act
        String viewName = theatreController.saveTheatre(theatre, bindingResult, model);

        // Assert
        verify(theatreService, times(1)).saveTheatre(theatre); // Ensure save was called
        assertEquals("theatre-form", viewName); // Return to form view
        verify(model, times(1)).addAttribute("errorMessage", "Database error"); // Ensure error message is set
    }

    @Test
    void testUpdateTheatre() {
        // Arrange
        int theatreId = 1;
        Theatre theatre = new Theatre();
        when(theatreService.getTheatreById(theatreId)).thenReturn(theatre);

        // Act
        String viewName = theatreController.updateTheatre(theatreId, model);

        // Assert
        assertEquals("theatre-form", viewName);
        verify(model, times(1)).addAttribute("theatre", theatre); // Ensure theatre is passed to model
    }

    @Test
    void testDeleteTheatre() {
        // Arrange
        int theatreId = 1;

        // Act
        String viewName = theatreController.deleteTheatre(theatreId);

        // Assert
        verify(theatreService, times(1)).deleteTheatreById(theatreId); // Ensure delete was called
        assertEquals("redirect:/theatres", viewName); // Check the redirect
    }

    @Test
    void testShowTheatres() {
        // Arrange
        List<Theatre> theatres = new ArrayList<>();
        theatres.add(new Theatre(1, "Theatre 1"));
        theatres.add(new Theatre(2, "Theatre 2"));

        // Mock behaviour
        when(theatreService.getAllTheatres()).thenReturn(theatres);

        // Act
        String viewName = theatreController.showTheatres(model);

        // Assert
        verify(theatreService, times(1)).getAllTheatres(); // Verify that the service method was called once
        verify(model, times(1)).addAttribute("theatres", theatres); // Verify that theatres were added to the model
        assertEquals("theatre", viewName); // Verify that the correct view is returned
    }

    @Test
    void testAddTheatre() {
        // Act
        String viewName = theatreController.addTheatre(model);

        // Assert
        verify(model, times(1)).addAttribute(eq("theatre"), any(Theatre.class)); // Verify that a new Theatre object is added to the model
        assertEquals("theatre-form", viewName); // Verify that the correct view is returned
    }
}
