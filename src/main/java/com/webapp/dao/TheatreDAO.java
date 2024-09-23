package com.webapp.dao;

import com.webapp.entities.Theatre;

import java.util.List;

public interface TheatreDAO {

    public List<Theatre> getAllTheatres();
    public void saveTheatre(Theatre theatre);
    public boolean existsByNameAndLocation(String name, String location);
    public Theatre getTheatreById(int id);
    public void deleteTheatreById(int id);
}
