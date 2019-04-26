package com.laszczka.projekt.spring.services;

import com.laszczka.projekt.spring.models.Movie;
import com.laszczka.projekt.spring.models.MovieType;

import java.util.List;

public interface MovieService {

    List<MovieType> getAllMovieTypes();
    List<Movie> getAllMovie();
    Movie getMovie(Integer id);
    MovieType getMovieType(Integer id);
    void deleteMovieType(Integer id);
    void saveMovietype(MovieType movieType);
    void deleteMovie(Integer id);
    void saveMovie(Movie movie);

}
