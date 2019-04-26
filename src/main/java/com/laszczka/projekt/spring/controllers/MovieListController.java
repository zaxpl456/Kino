package com.laszczka.projekt.spring.controllers;


import com.laszczka.projekt.spring.models.Movie;
import com.laszczka.projekt.spring.models.MovieType;
import com.laszczka.projekt.spring.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class MovieListController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "/movieTypeList.html",method = {RequestMethod.GET,RequestMethod.POST})
    public String showMovieTypesList(Model model, @Valid @ModelAttribute("movieType") MovieType movieType, BindingResult result){
       model.addAttribute("movieTypeListPage",movieService.getAllMovieTypes()) ;
       return "movieTypeList";
    }


    @Secured("ROLE_ADMIN")
    @GetMapping(path="/movieTypeList.html", params={"did"})
    public String deleteMovieType(int did, HttpServletRequest request){
        movieService.deleteMovieType(did);
        String queryString = prepareQueryString(request.getQueryString());
        return String.format("redirect:movieTypeList.html%s", queryString);//robimy przekierowanie, ale zachowując parametry pageingu
    }


    private String prepareQueryString(String queryString) {//np., did=20&page=2&size=20
        if (queryString.contains("&")) {
            return "?"+queryString.substring(queryString.indexOf("&") + 1);//obcinamy parametr did, bo inaczej po przekierowaniu znowu będzie wywołana metoda deleteVihicle
        }else{
            return "";
        }
    }


    @RequestMapping(value="/movieList.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String showMovieList(Model model, @Valid @ModelAttribute("movieList") Movie movie, BindingResult result){
        model.addAttribute("movieListPage", movieService.getAllMovie());
        return "movieList";
    }


    @Secured("IS_AUTHENTICATED_FULLY")
    @RequestMapping(value="/movieList.html", params = "id", method = RequestMethod.GET)
    public String showVehicleDetails(Model model, Integer id){
        model.addAttribute("movie", movieService.getMovie(id));
        return "movieDetails";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path="/movieList.html", params={"did"})
    public String deleteMovieT(int did, HttpServletRequest request){
        movieService.deleteMovie(did);
        String queryString = prepareQueryString(request.getQueryString());
        return String.format("redirect:movieList.html%s", queryString);//robimy przekierowanie, ale zachowując parametry pageingu
    }
}
