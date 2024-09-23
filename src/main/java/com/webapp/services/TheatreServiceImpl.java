package com.webapp.services;

import com.webapp.dao.TheatreDAO;
import com.webapp.entities.Theatre;
import org.springframework.stereotype.Service;
import com.webapp.exceptions.TheatreAlreadyExistsException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TheatreServiceImpl implements TheatreService{

   private TheatreDAO theatreDAO;

    // Constructor injection
    public void theatreService(TheatreDAO theatreDAO) {
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
