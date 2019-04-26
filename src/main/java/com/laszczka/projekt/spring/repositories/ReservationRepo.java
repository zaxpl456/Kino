package com.laszczka.projekt.spring.repositories;

import com.laszczka.projekt.spring.models.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.swing.*;
import java.util.Date;
import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation,Integer> , JpaSpecificationExecutor<Reservation> {

   List<Reservation> findReservationByShowIdAndTimesId(int show, int times);

   Page<Reservation> findReservationByUserSurnameAndShowShowDate(String phrase,Date date,Pageable pageable);


}
