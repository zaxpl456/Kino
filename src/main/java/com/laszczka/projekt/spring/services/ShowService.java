package com.laszczka.projekt.spring.services;


import com.laszczka.projekt.spring.models.Show;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ShowService {

    List<Show> getAllShows();
    Show getShow(Integer id);
    void deleteShow(int id);
    void saveShow(Show show);

    List<Show> findShowByDate(Date date);
}
