package com.laszczka.projekt.spring.services;

import com.laszczka.projekt.spring.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    void save(User user);



    boolean isUniqueLogin(String login);

    User  findByName(String name);

    User  findById(int id);





}
