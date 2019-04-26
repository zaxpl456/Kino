package com.laszczka.projekt.spring.controllers;


import com.laszczka.projekt.spring.models.Show;
import com.laszczka.projekt.spring.services.MovieService;
import com.laszczka.projekt.spring.services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.datetime.joda.JodaTimeContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class ShowListController {

    @Autowired
    ShowService showService;

    @Autowired
    MovieService movieService;

    private String prepareQueryString(String queryString) {//np., did=20&page=2&size=20
        if (queryString.contains("&")) {
            return "?"+queryString.substring(queryString.indexOf("&") + 1);//obcinamy parametr did, bo inaczej po przekierowaniu znowu będzie wywołana metoda deleteVihicle
        }else{
            return "";
        }
    }




    @RequestMapping(value="/showList.html",method = {RequestMethod.GET, RequestMethod.POST})
    public String showShowList(Model model, @Valid @ModelAttribute("show") Show show ,BindingResult result, Pageable pageable){

        LocalDate localDate=LocalDate.now();

        Date date=Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());


        model.addAttribute("showListPage", showService.findShowByDate(date));
        return "showList";
    }


    @Secured("IS_AUTHENTICATED_FULLY")
    @RequestMapping(value="/showList.html", params = "id", method = RequestMethod.GET)
    public String showMovieDetails(Model model, Integer id){
        model.addAttribute("movie",movieService.getMovie(showService.getShow(id).getMovie().getId()));
        return "movieDetails";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path="/showList.html", params={"did"})
    public String deleteMovieT(int did, HttpServletRequest request){
        showService.deleteShow(did);
        String queryString = prepareQueryString(request.getQueryString());
        return String.format("redirect:showList.html%s", queryString);//robimy przekierowanie, ale zachowując parametry pageingu
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path="/showList.html", params={"day"})
    public String findShows(int day, HttpServletRequest request, Model model){
        LocalDate localDate=LocalDate.now().plusDays(day);

        Date date=Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());


        model.addAttribute("showListPage", showService.findShowByDate(date));
        return "showList";
    }









}
