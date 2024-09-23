package com.webapp.services;

import com.webapp.dao.TheatreDAO;
import com.webapp.entities.Theatre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TheatreServiceImpl implements TheatreService{

   private TheatreDAO theatreDAO;

    // Constructor injection
    public void TheatreService(TheatreDAO theatreDAO) {
        this.theatreDAO = theatreDAO;
    }


    @Override
    public List<Theatre> getAllTheatres() {
        return theatreDAO.getAllTheatres();
    }

    @Override
    public void saveTheatre(Theatre theatre) {
        if(theatreDAO.existsByNameAndLocation(theatre.getName(), theatre.getLocation()))
            throw new TheatreAlreadyExistsException("A theatre with the same name and location already exists!");
        theatreDAO.saveTheatre(theatre);
    }

    @Override
    public Theatre getTheatreById(int id) {
        return theatreDAO.getTheatreById(id);
    }

    @Override
    public void deleteTheatreById(int id) {
        theatreDAO.deleteTheatreById(id);
    }


}
