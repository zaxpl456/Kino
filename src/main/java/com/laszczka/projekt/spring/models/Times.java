package com.laszczka.projekt.spring.models;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "times")
@Data
@Getter
@Setter
@NoArgsConstructor
public class Times {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Temporal(TemporalType.TIME)
    private Date time;
}
