package com.laszczka.projekt.spring.controllers;


import com.laszczka.projekt.spring.models.Hall;
import com.laszczka.projekt.spring.models.MovieType;
import com.laszczka.projekt.spring.services.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class HallController {

    @Autowired
    HallService hallService;

    @RequestMapping(value="/hallForm.html",method = RequestMethod.GET)
    public String showMovieTypeForm(Model model, Optional<Integer> id){
        model.addAttribute("hall",
                id.isPresent()?
                        hallService.getHall(id.get()):
                        new Hall());

        return "hallForm";


    }


    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/hallForm.html",method = RequestMethod.POST)
    public String processHallForm(@Valid @ModelAttribute("hall") Hall hall, BindingResult errors){

        if(errors.hasErrors()){
            return "hallForm";
        }
        hallService.saveHall(hall);
        return "redirect:hallList.html";
    }


    @RequestMapping(value = "/hallList.html",method = {RequestMethod.GET,RequestMethod.POST})
    public String showMovieTypesList(Model model, @Valid @ModelAttribute("hall") Hall hall, BindingResult result){
        model.addAttribute("hallListPage",hallService.getAllHalls()) ;
        return "hallList";
    }


    @Secured("ROLE_ADMIN")
    @GetMapping(path="/hallList.html", params={"did"})
    public String deleteMovieType(int did, HttpServletRequest request){
        hallService.deleteHall(did);
        String queryString = prepareQueryString(request.getQueryString());
        return String.format("redirect:hallList.html%s", queryString);//robimy przekierowanie, ale zachowując parametry pageingu
    }

    private String prepareQueryString(String queryString) {//np., did=20&page=2&size=20
        if (queryString.contains("&")) {
            return "?"+queryString.substring(queryString.indexOf("&") + 1);//obcinamy parametr did, bo inaczej po przekierowaniu znowu będzie wywołana metoda deleteVihicle
        }else{
            return "";
        }
    }
}
