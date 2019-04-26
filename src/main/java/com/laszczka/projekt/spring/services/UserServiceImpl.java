package com.laszczka.projekt.spring.services;

import com.laszczka.projekt.spring.models.Role;
import com.laszczka.projekt.spring.repositories.RoleRepo;
import com.laszczka.projekt.spring.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service("userDetailsService")
@EnableTransactionManagement
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private RoleRepo roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    //bez adnotacji @Transactional sesja jest zamykana po wywołaniu findByUsername, co uniemożliwia dociągnięcie ról, gdyż fetch=EAGER.
    //ponadto, musi być włączone zarządzanie transakcjami @EnableTransactionManagement
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.laszczka.projekt.spring.models.User user = userRepository.findByUsername(email);



        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return createUserDetails(user);
    }



    private UserDetails createUserDetails(com.laszczka.projekt.spring.models.User user) {
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        for (Role role : user.getRoles()){
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getType().toString()));
//        }

        Set<GrantedAuthority> grantedAuthorities =
                user.getRoles().stream().map(//mapowanie Role na GrantedAuthority
                        r -> new SimpleGrantedAuthority(r.getType().toString())
                ).collect(Collectors.toSet());

        return new User(user.getUsername(), user.getPassword(), true, true, true, true, grantedAuthorities);
    }



    @Override
    public void save(com.laszczka.projekt.spring.models.User user) {
        Role userRole = roleRepository.findRoleByType(Role.Types.ROLE_USER);
        List roles = Arrays.asList(userRole);
        user.setRoles(new HashSet<>(roles));
        user.getUsername();
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPasswordConfirm(null);//wyzerowanie jest potrzebne ze względu na walidację
        userRepository.saveAndFlush(user);
    }

    @Override
    public boolean isUniqueLogin(String username) {
        return userRepository.findByUsername(username) == null;
    }

    @Override
    public com.laszczka.projekt.spring.models.User findByName(String name) {
       return userRepository.findByUsername(name);
    }

    @Override
    public com.laszczka.projekt.spring.models.User findById(int id) {
        return userRepository.findById(id);
    }


}
