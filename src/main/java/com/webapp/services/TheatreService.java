package com.webapp.services;

import com.webapp.entities.Theatre;

import java.util.List;

public interface TheatreService {

    public List<Theatre> getAllTheatres();
    public void saveTheatre(Theatre theatre);
    public Theatre getTheatreById(int id);
    public void deleteTheatreById(int id);

}
