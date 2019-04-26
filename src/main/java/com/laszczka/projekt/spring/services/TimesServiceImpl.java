package com.laszczka.projekt.spring.services;

import com.laszczka.projekt.spring.models.Times;
import com.laszczka.projekt.spring.repositories.TimesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimesServiceImpl implements TimesService {

    @Autowired
    TimesRepo timesRepo;


    @Override
    public List<Times> getAllTimes() {
        return timesRepo.findAll();
    }

    @Override
    public Times getTimes(int id) {
        return timesRepo.findById(id).get();
    }

    @Override
    public void deleteTimes(int id) {
        timesRepo.deleteById(id);

    }

    @Override
    public void saveTimes(Times times) {
        timesRepo.save(times);
    }
}
