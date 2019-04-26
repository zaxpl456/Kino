package com.laszczka.projekt.spring.controllers;

import com.laszczka.projekt.spring.models.User;
import com.laszczka.projekt.spring.services.EmailSender;
import com.laszczka.projekt.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller

public class UserRegistrationFormController {

    @Autowired
    private UserService userService;

    @Autowired
    EmailSender emailSender;

    @GetMapping("/registrationForm.html")
    public String registration(Model model) {
        model.addAttribute("userCommand", new User());
        return "registrationForm";
    }

    @PostMapping("/registrationForm.html")
    public String registration(@Valid @ModelAttribute("userCommand") User userForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "registrationForm";
        }
        userForm.getUsername();
        userService.save(userForm);
        emailSender.sendEmail(userForm.getEmail(),"Potwierdzenie rejestracji","Witaj "+userForm.getName()+" twoja rezerwacja w systemie została potwierdzona.");
        return "registrationSuccess";
    }


    @RequestMapping( path="/registrationComplete.html",method = RequestMethod.GET, params = {"email"})
    public String registrationSucces(String email) {

           userService.loadUserByUsername(email);









        return "registrationComplete";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //aby użytkownik nie mógł sobie wstrzyknąć aktywacji konta oraz ról (np., ADMIN)
        //roles są na wszelki wypadek, bo warstwa serwisów i tak ustawia ROLE_USER dla nowego usera
        binder.setDisallowedFields("enabled", "roles");
    }

}

