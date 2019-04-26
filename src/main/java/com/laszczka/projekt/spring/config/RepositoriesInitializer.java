package com.laszczka.projekt.spring.config;


import com.laszczka.projekt.spring.models.MovieType;
import com.laszczka.projekt.spring.models.Role;
import com.laszczka.projekt.spring.models.User;
import com.laszczka.projekt.spring.repositories.MovieTypeRepo;
import com.laszczka.projekt.spring.repositories.RoleRepo;
import com.laszczka.projekt.spring.repositories.UserRepo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@Configuration
public class RepositoriesInitializer {


    @Autowired
    private UserRepo userRepository;
    @Autowired
    private RoleRepo roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MovieTypeRepo movieTypeRepo;

    @Bean
    InitializingBean init() {

        return () -> {









            if(userRepository.findAll().isEmpty() == true){
                try {
                    Role roleUser = roleRepository.save(new Role(Role.Types.ROLE_USER));
                    Role roleAdmin = roleRepository.save(new Role(Role.Types.ROLE_ADMIN));

                    User user = new User("user", true);
                    user.setRoles(new HashSet<>(Arrays.asList(roleUser)));
                    user.setPassword(passwordEncoder.encode("user"));

                    User admin = new User("admin", true);
                    admin.setRoles(new HashSet<>(Arrays.asList(roleAdmin)));
                    admin.setPassword(passwordEncoder.encode("admin"));

                    User test = new User("test", true);
                    test.setRoles(new HashSet<>(Arrays.asList(roleAdmin, roleUser)));
                    test.setPassword(passwordEncoder.encode("test"));

                    userRepository.save(user);
                    userRepository.save(admin);
                    userRepository.save(test);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

        };
    }


}
