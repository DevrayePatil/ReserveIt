package org.example.reserveit.services;

import org.example.reserveit.exceptions.MovieNotFoundException;
import org.example.reserveit.models.Movie;

import java.util.List;

public interface MovieService {
    Movie addMovie(String name, String description);
    Movie getMovie(Long id) throws MovieNotFoundException;
    Movie getMovieByName(String name) throws MovieNotFoundException;
    Movie updateMovie(Long id, String name, String description) throws MovieNotFoundException;
    Movie deleteMovie(Long id) throws MovieNotFoundException;
    List<Movie> getMovies();
}
