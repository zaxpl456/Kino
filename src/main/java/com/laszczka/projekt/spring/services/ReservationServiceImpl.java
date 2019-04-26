package com.laszczka.projekt.spring.services;

import com.laszczka.projekt.spring.models.Reservation;
import com.laszczka.projekt.spring.models.commands.ReservationFilter;
import com.laszczka.projekt.spring.repositories.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepo reservationRepo;

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepo.findAll();
    }

    @Override
    public Page<Reservation> getAllReservations(ReservationFilter reservationFilter, Pageable pageable) {
        Page page;
        page = reservationRepo.findReservationByUserSurnameAndShowShowDate(reservationFilter.getPhrase(),reservationFilter.getDate(),pageable);

        return page;
    }

    @Override
    public Reservation getReservations(Integer id) {
        return reservationRepo.findById(id).get();
    }

    @Override
    public List<Reservation> getReservationsByShowIdAndTime(Integer showid, Integer time) {
        return reservationRepo.findReservationByShowIdAndTimesId(showid,time);
    }

    @Override
    public void deleteReservation(int id) {
        reservationRepo.deleteById(id);
        }



    @Override
    public void saveReservation(Reservation reservation) {
        reservationRepo.save(reservation);

    }
}
