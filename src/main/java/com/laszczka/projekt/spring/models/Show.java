package com.laszczka.projekt.spring.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "show")
@Getter
@Setter
@NoArgsConstructor
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(name = "show_date")
    private Date showDate;

    @Valid
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id",nullable = false)
    private Movie movie;

    @Valid
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hall_id",nullable = false)
    private Hall hall;

    @ManyToMany(fetch = FetchType.LAZY)//LAZY powoduje dociągnięcie tych elementów dopiero wtedy, gdy są używane
    private List<Times> times;


}
