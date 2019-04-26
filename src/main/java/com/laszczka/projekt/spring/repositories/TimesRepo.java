package com.laszczka.projekt.spring.repositories;

import com.laszczka.projekt.spring.models.Times;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimesRepo extends JpaRepository<Times,Integer> {
}
