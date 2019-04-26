package com.laszczka.projekt.spring.services;

import com.laszczka.projekt.spring.models.Movie;
import com.laszczka.projekt.spring.models.MovieType;
import com.laszczka.projekt.spring.repositories.MovieRepo;
import com.laszczka.projekt.spring.repositories.MovieTypeRepo;
import javassist.NotFoundException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieTypeRepo movieTypeRepo;

    @Autowired
    private MovieRepo movieRepo;


    @Override
    public List<MovieType> getAllMovieTypes() {
        return movieTypeRepo.findAll();
    }

    @Override
    public List<Movie> getAllMovie() {
        return movieRepo.findAll();
    }

    @Override
    public Movie getMovie(Integer id) {
        return movieRepo.findById(id).get();
    }

    @Override
    public MovieType getMovieType(Integer id) {
        Optional<MovieType> optionalMovieType=movieTypeRepo.findById(id);
        MovieType movieType=optionalMovieType.orElseThrow(()-> new ObjectNotFoundException(id,"Nie znaleziono"));

        return movieType;


    }

    @Override
    @Transactional
    public void deleteMovieType(Integer id) {
        if(movieTypeRepo.existsById(id)) {
            movieTypeRepo.delete(movieTypeRepo.getOne(id));
        }
        else{
            throw new ObjectNotFoundException(id,"Nie znaleziono gatunku filmu");
        }

    }

    @Override
    public void saveMovietype(MovieType movieType) {
        movieTypeRepo.save(movieType);

    }

    @Override
    @Transactional
    public void deleteMovie(Integer id) {
        if(movieRepo.existsById(id)) {
            movieRepo.delete(movieRepo.getOne(id));
        }
        else{
            throw new ObjectNotFoundException(id,"Nie znaleziono filmu");
        }

    }

    @Override
    public void saveMovie(Movie movie) {

        movieRepo.save(movie);

    }
}
