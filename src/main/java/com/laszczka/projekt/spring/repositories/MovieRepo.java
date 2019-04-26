package com.laszczka.projekt.spring.repositories;

import com.laszczka.projekt.spring.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepo extends JpaRepository<Movie,Integer> {
}
