package com.laszczka.projekt.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {



    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public AccessDeniedHandler createAccessDeniedHandler(){
        AccessDeniedHandlerImpl impl = new AccessDeniedHandlerImpl();
        impl.setErrorPage("/error403");
        impl.setErrorPage("/error404");//url
        return impl;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/statics/**", "/webjars/**", "/","/home.html,","/showList.html","/registrationForm.html","/registrationSuccess.html","/registrationComplete.html**").permitAll()
                .antMatchers( "/movieTypeForm.html","/movieForm.html").hasRole("ADMIN")
                .antMatchers( "/reservationConfirm.html","/reservationForm.html","/reservationSuccess.html").hasRole("USER")
                .anyRequest().authenticated();//każde żądanie ma być uwierzytelnione
        http
                .formLogin()//pozwól użytkownikom uwierzytelniać się poprzez formularz
                .loginPage("/login")//formularz logowania dostępny jest pod URL
                .permitAll();//przyznaj dostęp wszystkim użytkownikom dla wszystkich URL powiązanych z logowaniem opartym na formularzu.
        http
                .logout()//pozwól wszystkim użykownikom się wylogować
                .permitAll();//

        http.exceptionHandling().accessDeniedHandler(createAccessDeniedHandler());

    }

}


