package org.example.reserveit.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.reserveit.models.Movie;

import java.util.List;

@Getter
@Setter
@Builder
public class MovieDto {
    private Long id;
    private String name;
    private String description;

    public static MovieDto from(Movie movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .name(movie.getName())
                .description(movie.getDescription())
                .build();
    }

    public static List<MovieDto> from(List<Movie> movies) {
        return movies.stream().map(MovieDto::from).toList();
    }

}
