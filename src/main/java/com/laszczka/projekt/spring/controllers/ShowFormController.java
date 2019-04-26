package com.laszczka.projekt.spring.controllers;


import com.laszczka.projekt.spring.models.*;
import com.laszczka.projekt.spring.services.HallService;
import com.laszczka.projekt.spring.services.MovieService;
import com.laszczka.projekt.spring.services.ShowService;
import com.laszczka.projekt.spring.services.TimesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class ShowFormController {

   @Autowired
    ShowService showService;

   @Autowired
    TimesService timesService;

   @Autowired
    HallService hallService;

   @Autowired
    MovieService movieService;





    @RequestMapping(value="/showForm.html",method = RequestMethod.GET)
    public String showForm(Model model, Optional<Integer> id){
        model.addAttribute("show",
                id.isPresent()?
                        showService.getShow(id.get()):
                        new Show());

        return "showForm";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/showForm.html", method= RequestMethod.POST)
    //@ResponseStatus(HttpStatus.CREATED)
    public String processShowForm(@Valid @ModelAttribute("show")Show show, BindingResult errors){

        if(errors.hasErrors()){
            System.out.println(errors.toString());
            return "showForm";
        }

        showService.saveShow(show);

        return "redirect:showList.html";//po udanym dodaniu/edycji przekierowujemy na listę
    }

    @ModelAttribute("movie")
    public List<Movie> loadMovies(){
        List<Movie> movies = movieService.getAllMovie();
        return movies;
    }

    @ModelAttribute("movieType")
    public List<MovieType> loadTypesMovies(){
        List<MovieType> movieType = movieService.getAllMovieTypes();
        return movieType;
    }


    @ModelAttribute("halls")
    public List<Hall> loadTypes(){
        List<Hall> halls = hallService.getAllHalls();
        return halls;
    }

    @ModelAttribute("timess")
    public List<Times> loadTimes(){
        List<Times> times = timesService.getAllTimes();
        return times;
    }




}
