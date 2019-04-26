package com.laszczka.projekt.spring.services;

import com.laszczka.projekt.spring.models.Reservation;
import com.laszczka.projekt.spring.models.commands.ReservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ReservationService {
    Page<Reservation> getAllReservations(ReservationFilter reservationFilter, Pageable pageable);
    List<Reservation> getAllReservations();

    Reservation getReservations(Integer id);
    List<Reservation> getReservationsByShowIdAndTime(Integer showid,Integer time);
    void deleteReservation(int id);
    void saveReservation(Reservation reservation);


}
