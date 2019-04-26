package com.laszczka.projekt.spring.controllers;


import com.laszczka.projekt.spring.models.commands.ReservationFilter;
import com.laszczka.projekt.spring.repositories.ReservationRepo;
import com.laszczka.projekt.spring.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@SessionAttributes("searchCommand")
public class ReservationListController {

    @Autowired
    ReservationService reservationService;

    @ModelAttribute("searchCommand")
    public ReservationFilter getSimpleSearch() {
        return new ReservationFilter();
    }

    @GetMapping(value = "/reservationList.html", params = {"all"})
    public String resetehicleList(@ModelAttribute("searchCommand") ReservationFilter search) {
        search.clear();
        return "redirect:reservationList.html";
    }


    @RequestMapping(value = "/reservationList.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String showReservationList(Model model, @Valid @ModelAttribute("searchCommand") ReservationFilter search, BindingResult result, Pageable pageable) {
        model.addAttribute("reservationListPage", reservationService.getAllReservations(search, pageable));
        return "reservationList";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/reservationList.html", params = {"did"})
    public String deleteReservation(int did, HttpServletRequest request) {
        reservationService.deleteReservation(did);
        String queryString = prepareQueryString(request.getQueryString());
        return String.format("redirect:reservationList.html%s", queryString);//robimy przekierowanie, ale zachowując parametry pageingu
    }


    private String prepareQueryString(String queryString) {//np., did=20&page=2&size=20
        if (queryString.contains("&")) {
            return "?" + queryString.substring(queryString.indexOf("&") + 1);//obcinamy parametr did, bo inaczej po przekierowaniu znowu będzie wywołana metoda deleteVihicle
        } else {
            return "";
        }


    }

}
