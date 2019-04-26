package com.laszczka.projekt.spring.models.commands;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.Date;

@Getter
@Setter
public class ReservationFilter {

    String phrase;


    Date date;





    public String getPhraseLIKE(){
        if(StringUtils.isEmpty(phrase)) {
            return null;
        }else{
            return "%"+phrase+"%";
        }
    }

    public void clear(){
        this.phrase="";
        this.date=null;


    }


    public boolean isEmpty(){
        return StringUtils.isEmpty(phrase) && date==null  ;
    }


}
