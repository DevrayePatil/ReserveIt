package org.example.reserveit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.reserveit.models.*;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreenDto {

    private Long id;
    private String name;
    private TheatreDto theatre;
    private List<Feature> features;
    private List<SeatDto> seats;
    private ScreenStatus status;

    public static ScreenDto from(Screen screen) {
        return ScreenDto.builder()
                .id(screen.getId())
                .name(screen.getName())
                .theatre(TheatreDto.fromWithoutScreen(screen.getTheatre()))
                .features(screen.getFeatures())
                .seats(SeatDto.fromMinimal(screen.getSeats()))
                .status(screen.getStatus())
                .build();
    }

    public static ScreenDto fromMinimal(Screen screen) {
        return ScreenDto.builder()
                .id(screen.getId())
                .name(screen.getName())
                .status(screen.getStatus())
                .build();
    }

    public static ScreenDto fromWithoutTheatre(Screen screen) {
        return ScreenDto.builder()
                .id(screen.getId())
                .name(screen.getName())
                .features(screen.getFeatures())
                .seats(SeatDto.from(screen.getSeats()))
                .status(screen.getStatus())
                .build();
    }

    public static List<ScreenDto> from(List<Screen> screens) {
        return screens.stream().map(ScreenDto::from).toList();
    }

    public static List<ScreenDto> fromMinimal(List<Screen> screens) {
        return screens.stream().map(ScreenDto::fromMinimal).toList();
    }
}
