package com.webapp.services;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.webapp.dao.TheatreDAO;
import com.webapp.entities.Theatre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

class TheatreServiceImplTest {

    @InjectMocks
    private TheatreServiceImpl theatreService;

    @Mock
    private TheatreDAO theatreDAO;

    private Theatre theatre1;
    private Theatre theatre2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        theatre1 = new Theatre("Theatre One", "Location A", null);
        theatre2 = new Theatre("Theatre Two", "Location B", null);
    }

    @Test
    void testGetAllTheatres() {
        List<Theatre> expectedTheatres = new ArrayList<>();
        expectedTheatres.add(theatre1);
        expectedTheatres.add(theatre2);

        when(theatreDAO.getAllTheatres()).thenReturn(expectedTheatres);

        List<Theatre> actualTheatres = theatreService.getAllTheatres();

        assertEquals(expectedTheatres, actualTheatres);
        assertEquals(2, actualTheatres.size());
        verify(theatreDAO, times(1)).getAllTheatres();
    }

    @Test
    void testSaveTheatre() {
        when(theatreDAO.existsByNameAndLocation(theatre1.getName(), theatre1.getLocation())).thenReturn(false);

        when(theatreDAO.getAllTheatres()).thenReturn(new ArrayList<>());

        theatreService.saveTheatre(theatre1);

        verify(theatreDAO, times(1)).saveTheatre(theatre1);

        List<Theatre> updatedTheatres = new ArrayList<>();
        updatedTheatres.add(theatre1);
        when(theatreDAO.getAllTheatres()).thenReturn(updatedTheatres);

        List<Theatre> theatresAfterSave = theatreService.getAllTheatres();
        assertEquals(1, theatresAfterSave.size());
        assertTrue(theatresAfterSave.contains(theatre1));
    }


    @Test
    void testSaveTheatreWithDuplicateNameAndLocation() {
        when(theatreDAO.existsByNameAndLocation(theatre1.getName(), theatre1.getLocation())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            theatreService.saveTheatre(theatre1);
        });

        assertEquals("A theatre with the same name and location already exists!", exception.getMessage());
        verify(theatreDAO, never()).saveTheatre(theatre1);
    }

    @Test
    void testGetTheatreById() {
        int theatreId = 1;
        when(theatreDAO.getTheatreById(theatreId)).thenReturn(theatre1);

        Theatre actualTheatre = theatreService.getTheatreById(theatreId);

        assertEquals(theatre1, actualTheatre);
        verify(theatreDAO, times(1)).getTheatreById(theatreId);
    }

    @Test
    void testDeleteTheatreById() {
        theatre1 = new Theatre();
        theatre1.setId(1);
        theatre1.setName("Cineplex");
        theatre1.setLocation("Downtown");

        List<Theatre> existingTheatres = new ArrayList<>();
        existingTheatres.add(theatre1);
        when(theatreDAO.getAllTheatres()).thenReturn(existingTheatres);

        assertEquals(1, theatreService.getAllTheatres().size());

        theatreService.deleteTheatreById(theatre1.getId());

        verify(theatreDAO, times(1)).deleteTheatreById(theatre1.getId());

        existingTheatres.remove(theatre1);
        when(theatreDAO.getAllTheatres()).thenReturn(existingTheatres);

        List<Theatre> theatresAfterDelete = theatreService.getAllTheatres();
        assertEquals(0, theatresAfterDelete.size());
    }

}
