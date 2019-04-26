package com.laszczka.projekt.spring.services;

import com.laszczka.projekt.spring.models.Hall;

import java.util.List;

public interface HallService {
    List<Hall> getAllHalls();
    Hall getHall(Integer id);
    void deleteHall(int id);
    void saveHall(Hall hall);
}
