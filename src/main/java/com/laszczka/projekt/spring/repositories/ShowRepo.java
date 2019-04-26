package com.laszczka.projekt.spring.repositories;

import com.laszczka.projekt.spring.models.Show;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface ShowRepo extends JpaRepository<Show,Integer> {
    List<Show> findByShowDate(Date date);

}
