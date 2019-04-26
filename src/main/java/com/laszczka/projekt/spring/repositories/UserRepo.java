package com.laszczka.projekt.spring.repositories;

import com.laszczka.projekt.spring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepo extends JpaRepository<User,Integer> {
User findByEmail(String email);
User findByUsername(String username);
User findById(int id);


}
