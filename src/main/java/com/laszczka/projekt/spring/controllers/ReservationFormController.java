package com.laszczka.projekt.spring.controllers;


import com.laszczka.projekt.spring.models.Reservation;
import com.laszczka.projekt.spring.models.User;
import com.laszczka.projekt.spring.services.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Controller
@SessionAttributes("reservation")
public class ReservationFormController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    ShowService showService;

    @Autowired
    TimesService timesService;

    @Autowired
    HallService hallService;

    @Autowired
    UserService userService;

    @Autowired
    EmailSender emailSender;


    List<Integer> list=new ArrayList<>();








    @Secured("IS_AUTHENTICATED_FULLY")
    @RequestMapping(value="/reservationForm.html", params = {"timeid","showid"}, method = RequestMethod.GET)
    public String setReservation(Model model ,Integer timeid, Integer showid, Optional<Integer> id){
      List<Integer> zajete=new ArrayList<>();

        for(int i=0;i<reservationService.getReservationsByShowIdAndTime(showid,timeid).size();i++){
            zajete.addAll(reservationService.getReservationsByShowIdAndTime(showid,timeid).get(i).getSeat());
        }
        zajete.size();


        Reservation r=new Reservation();
        List<Integer> list=new ArrayList<>();
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        User u=  userService.findByName(authentication.getName());
        r.setShow(showService.getShow(showid));
        r.setTimes(timesService.getTimes(timeid));
        r.setUser(u);
        for(int i=1;i<=hallService.getHall(showService.getShow(showid).getHall().getId()).getSeats();i++){
            list.add(i);

        }
        for(int i=0;i<list.size();i++) {
            for (int j = 0; j < zajete.size(); j++) {
                if (zajete.get(j) == list.get(i)){
                    list.set(i,0);
                }
            }
        }




        model.addAttribute("reservation",
                id.isPresent()?
                        reservationService.getReservations(id.get()):
                        r);
        model.addAttribute("seats",list);





        return "reservationForm";
    }



    @RequestMapping(value="/reservationForm.html", method= RequestMethod.POST)
    public String processShowForm(@Valid @ModelAttribute("reservation")Reservation reservation, BindingResult errors){

        if(errors.hasErrors()){
            System.out.println(errors.toString());
            return "reservationForm";
        }





        return "reservationConfirm";//po udanym dodaniu/edycji przekierowujemy na listę
    }


    @RequestMapping(value="/reservationConfirm.html", method= RequestMethod.POST)
    public String reservation(@Valid @SessionAttribute("reservation")Reservation reservation, BindingResult errors,Model model){



        model.addAttribute("reservation",reservation);




        return "reservationConfirm";//po udanym dodaniu/edycji przekierowujemy na listę
    }

    @RequestMapping(value="/reservationSuccess.html", method= RequestMethod.POST)
    public String reservationSucces(@Valid @SessionAttribute("reservation")Reservation reservation){



        reservationService.saveReservation(reservation);

        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        User u=  userService.findByName(authentication.getName());

        emailSender.sendEmail(u.getEmail(),"Rezerwacja","Witaj "+u.getName()+" potwierdziliśmy twoją rezerwację w systemie" +
                "<br>" +
                "<br>"+" Nazwa filmu: "+reservation.getShow().getMovie().getTitle() +
                "<br>"+" Data: " + reservation.getShow().getShowDate().toString()+
                "<br>"+" Godzina: " + reservation.getTimes().getTime().toString()+
                "<br>"+" Miejsca: " + reservation.getSeat().toString());



        return "reservationSuccess";//po udanym dodaniu/edycji przekierowujemy na listę
    }



}
