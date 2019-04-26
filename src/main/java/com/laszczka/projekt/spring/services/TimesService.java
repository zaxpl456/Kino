package com.laszczka.projekt.spring.services;

import com.laszczka.projekt.spring.models.Hall;
import com.laszczka.projekt.spring.models.Times;

import java.util.List;

public interface TimesService {
    List<Times> getAllTimes();
    Times getTimes(int id);
    void deleteTimes(int id);
    void saveTimes(Times times);
}
