package org.example.reserveit.services.impl;

import lombok.AllArgsConstructor;
import org.example.reserveit.exceptions.MovieNotFoundException;
import org.example.reserveit.models.Movie;
import org.example.reserveit.repositories.MovieRepository;
import org.example.reserveit.services.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Movie addMovie(String name, String description) {
        Movie movie = new Movie();
        movie.setName(name);
        movie.setDescription(description);
        return movieRepository.save(movie);
    }

    @Override
    public Movie getMovie(Long id) throws MovieNotFoundException {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found"));
    }

    @Override
    public Movie getMovieByName(String name) throws MovieNotFoundException {
        return movieRepository.findMovieByName(name)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found"));
    }

    @Override
    public Movie updateMovie(Long id, String name, String description) throws MovieNotFoundException {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Move not found"));

        if (name != null) {
            movie.setName(name);
        }
        if (description != null) {
            movie.setDescription(description);
        }

        return movieRepository.save(movie);
    }

    @Override
    public Movie deleteMovie(Long id) throws MovieNotFoundException {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie Not found"));
        movieRepository.delete(movie);

        return movie;
    }

    @Override
    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }
}
