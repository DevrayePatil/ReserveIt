package org.example.reserveit.controllers;

import lombok.AllArgsConstructor;
import org.example.reserveit.dto.MovieDto;
import org.example.reserveit.dto.requests.MovieRequest;
import org.example.reserveit.dto.requests.UpdateMovieRequest;
import org.example.reserveit.dto.responses.ApiResponse;
import org.example.reserveit.exceptions.MovieNotFoundException;
import org.example.reserveit.models.Movie;
import org.example.reserveit.services.MovieService;
import org.example.reserveit.utils.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<ApiResponse<MovieDto>> addMovie(@RequestBody MovieRequest request) {

        Movie movie = movieService.addMovie(request.getName(), request.getDescription());

        MovieDto movieDto = MovieDto.from(movie);

        ApiResponse<MovieDto> response = ResponseBuilder.created("Movie added successfully", movieDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieDto>> getMovie(@PathVariable("id") Long id)
            throws MovieNotFoundException {

        Movie movie = movieService.getMovie(id);

        MovieDto movieDto = MovieDto.from(movie);

        ApiResponse<MovieDto> response = ResponseBuilder.ok("Movie fetched successfully", movieDto);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieDto>> updateMovie(@PathVariable("id") Long id,
                                                             @RequestBody UpdateMovieRequest request)
            throws MovieNotFoundException {

        Movie movie = movieService.updateMovie(id, request.getName(), request.getDescription());

        MovieDto movieDto = MovieDto.from(movie);

        ApiResponse<MovieDto> response = ResponseBuilder.ok("Movie updated successfully", movieDto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieDto>> deleteMovie(@PathVariable("id") Long id)
            throws MovieNotFoundException{

        Movie movie = movieService.deleteMovie(id);

        MovieDto movieDto = MovieDto.from(movie);

        ApiResponse<MovieDto> response = ResponseBuilder.ok("Movie deleted successfully", movieDto);

        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Object>> getMovies(
            @RequestParam(value = "name", required = false) String name
    ) throws MovieNotFoundException {

        if (name != null && !name.isBlank()) {
            Movie movie = movieService.getMovieByName(name);
            MovieDto movieDto = MovieDto.from(movie);
            return ResponseEntity.ok(ResponseBuilder.ok("Movies fetched successfully", movieDto));

        }
        List<Movie> movies = movieService.getMovies();

        List<MovieDto> movieDtos = MovieDto.from(movies);


        return ResponseEntity.ok(ResponseBuilder.ok("Movies fetched successfully", movieDtos, movieDtos.size()));
    }

}
