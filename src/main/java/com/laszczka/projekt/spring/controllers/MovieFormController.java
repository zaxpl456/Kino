package com.laszczka.projekt.spring.controllers;

import com.laszczka.projekt.spring.models.Movie;
import com.laszczka.projekt.spring.models.MovieType;
import com.laszczka.projekt.spring.services.MovieService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
public class MovieFormController {

    private MovieService movieService;

    public MovieFormController(MovieService movieService){
        this.movieService=movieService;
    }

    @RequestMapping(value="/movieTypeForm.html",method = RequestMethod.GET)
        public String showMovieTypeForm(Model model, Optional<Integer> id){
        model.addAttribute("movieType",
                id.isPresent()?
                movieService.getMovieType(id.get()):
                new MovieType());

        return "movieTypeForm";

    }


    @RequestMapping(value="/movieForm.html",method = RequestMethod.GET)
    public String showMovieForm(Model model, Optional<Integer> id){
        model.addAttribute("movie",
                id.isPresent()?
                        movieService.getMovie(id.get()):
                        new Movie());

        return "movieForm";


    }


    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/movieTypeForm.html",method = RequestMethod.POST)
    public String processMovieTypeForm(@Valid @ModelAttribute("movieType") MovieType movieType, BindingResult errors){

        if(errors.hasErrors()){
            return "movieTypeForm";
        }
        movieService.saveMovietype(movieType);
        return "redirect:movieTypeList.html";
    }



    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/movieForm.html", method= RequestMethod.POST)
    //@ResponseStatus(HttpStatus.CREATED)
    public String processMovieForm(@Valid @ModelAttribute("movie")Movie movie, BindingResult errors){

        if(errors.hasErrors()){
            System.out.println(errors.toString());
            return "movieForm";
        }

        movieService.saveMovie(movie);

        return "redirect:movieList.html";//po udanym dodaniu/edycji przekierowujemy na listę
    }

    @ModelAttribute("movieTypes")
    public List<MovieType> loadTypes(){
        List<MovieType> types = movieService.getAllMovieTypes();
        return types;
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {//Rejestrujemy edytory właściwości

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm");
        dateFormat.setLenient(false);
        dateFormat1.setLenient(false);
        CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, false);
        CustomDateEditor dateEditor1 = new CustomDateEditor(dateFormat1, false);

        binder.registerCustomEditor(Date.class, "productionDate", dateEditor);
        binder.registerCustomEditor(Date.class, "duration", dateEditor1);

        binder.setDisallowedFields("createdDate");//ze względu na bezpieczeństwo aplikacji to pole nie może zostać przesłane w formularzu

    }



}
