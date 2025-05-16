package org.example.reserveit.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.reserveit.models.Feature;
import org.example.reserveit.models.Show;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class ShowDto {
    private Long id;
    private Long movieId;
    private String movieName;
    private ScreenDto screen;
    private Date startTime;
    private Date endTime;
    private List<ShowSeatDto> seats;
    private List<ShowSeatTypeDto> seatTypes;
    private List<Feature> features;

    public static ShowDto from(Show show) {
        return ShowDto.builder()
                .id(show.getId())
                .movieId(show.getMovie().getId())
                .movieName(show.getMovie().getName())
                .screen(ScreenDto.fromMinimal(show.getScreen()))
                .startTime(show.getStartTime())
                .endTime(show.getEndTime())
                .seats(ShowSeatDto.fromMinimal(show.getSeats()))
                .seatTypes(ShowSeatTypeDto.fromMinimal(show.getSeatTypes()))
                .features(show.getFeatures())
                .build();
    }

    public static List<ShowDto> from(List<Show> shows) {
        return shows.stream().map(ShowDto::from).toList();
    }
}
