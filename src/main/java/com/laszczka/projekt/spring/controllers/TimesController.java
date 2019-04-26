package com.laszczka.projekt.spring.controllers;

import com.laszczka.projekt.spring.models.Times;
import com.laszczka.projekt.spring.services.TimesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
public class TimesController {

    @Autowired
    TimesService timesService;

    @RequestMapping(value="/timesForm.html",method = RequestMethod.GET)
    public String showTimesForm(Model model, Optional<Integer> id){
        model.addAttribute("times",
                id.isPresent()?
                        timesService.getTimes(id.get()):
                        new Times());

        return "timesForm";


    }


    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/timesForm.html",method = RequestMethod.POST)
    public String processTimesForm(@Valid @ModelAttribute("times") Times times, BindingResult errors){

        if(errors.hasErrors()){
            return "timesForm";
        }
        timesService.saveTimes(times);
        return "redirect:timesList.html";
    }


    @RequestMapping(value = "/timesList.html",method = {RequestMethod.GET,RequestMethod.POST})
    public String showTimesList(Model model, @Valid @ModelAttribute("times") Times times, BindingResult result){
        model.addAttribute("timesListPage",timesService.getAllTimes()) ;
        return "timesList";
    }


    @Secured("ROLE_ADMIN")
    @GetMapping(path="/timesList.html", params={"did"})
    public String deleteTimes(int did, HttpServletRequest request){
        timesService.deleteTimes(did);
        String queryString = prepareQueryString(request.getQueryString());
        return String.format("redirect:timesList.html%s", queryString);//robimy przekierowanie, ale zachowując parametry pageingu
    }

    private String prepareQueryString(String queryString) {//np., did=20&page=2&size=20
        if (queryString.contains("&")) {
            return "?"+queryString.substring(queryString.indexOf("&") + 1);//obcinamy parametr did, bo inaczej po przekierowaniu znowu będzie wywołana metoda deleteVihicle
        }else{
            return "";
        }
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {//Rejestrujemy edytory właściwości

        SimpleDateFormat dateFormat1 = new SimpleDateFormat("kk:mm");

        dateFormat1.setLenient(false);

        CustomDateEditor dateEditor1 = new CustomDateEditor(dateFormat1, false);

        binder.registerCustomEditor(Date.class, "time", dateEditor1);


        binder.setDisallowedFields("createdDate");//ze względu na bezpieczeństwo aplikacji to pole nie może zostać przesłane w formularzu

    }
}
