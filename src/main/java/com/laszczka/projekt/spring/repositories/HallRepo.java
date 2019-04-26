package com.laszczka.projekt.spring.repositories;

import com.laszczka.projekt.spring.models.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallRepo extends JpaRepository<Hall,Integer> {
}
