package com.laszczka.projekt.spring.services;

import com.laszczka.projekt.spring.models.Show;
import com.laszczka.projekt.spring.repositories.ShowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ShowServiceImpl implements ShowService {
    @Autowired
    ShowRepo showRepo;

    @Override
    public List<Show> getAllShows() {
        return showRepo.findAll();
    }

    @Override
    public Show getShow(Integer id) {
        return showRepo.findById(id).get();
    }

    @Override
    public void deleteShow(int id) {
        showRepo.deleteById(id);
    }

    @Override
    public void saveShow(Show show) {
        showRepo.save(show);
    }

    @Override
    public List<Show> findShowByDate(Date date) {
        return showRepo.findByShowDate(date);
    }
}
