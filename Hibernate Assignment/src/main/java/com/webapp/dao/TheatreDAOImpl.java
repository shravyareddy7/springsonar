package com.webapp.dao;

import com.webapp.entities.Theatre;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TheatreDAOImpl implements TheatreDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Theatre> getAllTheatres() {
        Session session=sessionFactory.getCurrentSession();
        return session.createQuery("from Theatre").getResultList();
    }

    @Override
    public void saveTheatre(Theatre theatre) {
        Session session=sessionFactory.getCurrentSession();
        session.saveOrUpdate(theatre);
    }

    @Override
    public boolean existsByNameAndLocation(String name, String location) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Theatre WHERE name = :name AND location = :location";
        Query<Theatre> query = session.createQuery(hql, Theatre.class);
        query.setParameter("name", name);
        query.setParameter("location", location);

        return !query.getResultList().isEmpty();
    }

    @Override
    public Theatre getTheatreById(int id) {
        Session session=sessionFactory.getCurrentSession();
        return session.get(Theatre.class,id);
    }

    @Override
    public void deleteTheatreById(int id) {
        Session session=sessionFactory.getCurrentSession();
        Theatre theatre = session.get(Theatre.class, id);
        session.delete(theatre);
    }

}
