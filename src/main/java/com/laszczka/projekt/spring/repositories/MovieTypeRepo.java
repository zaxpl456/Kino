package com.laszczka.projekt.spring.repositories;

import com.laszczka.projekt.spring.models.Movie;
import com.laszczka.projekt.spring.models.MovieType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieTypeRepo extends JpaRepository<MovieType,Integer> {

}
