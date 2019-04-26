package com.laszczka.projekt.spring.services;

import com.laszczka.projekt.spring.models.Hall;
import com.laszczka.projekt.spring.repositories.HallRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallServiceImpl implements HallService {

    @Autowired
    HallRepo hallRepo;

    @Override
    public List<Hall> getAllHalls() {
        return hallRepo.findAll();
    }

    @Override
    public Hall getHall(Integer id) {
        return hallRepo.findById(id).get();
    }

    @Override
    public void deleteHall(int id) {
        hallRepo.deleteById(id);
    }

    @Override
    public void saveHall(Hall hall) {
        hallRepo.save(hall);
    }
}
