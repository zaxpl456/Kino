package com.laszczka.projekt.spring.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name="movies")
@Getter
@Setter
@NoArgsConstructor
public class Movie {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String photo;


    @Length(min = 2,max = 500)
    private String title;



    @Temporal(TemporalType.TIME)
    private Date duration;

    @Past
    @Temporal(TemporalType.DATE)
    @Column(name="production_date")
    private Date productionDate;


    @Valid
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="movie_types_id", nullable = false)
    private MovieType movieType;


    @Length(max=10000)
    private String details;


}
