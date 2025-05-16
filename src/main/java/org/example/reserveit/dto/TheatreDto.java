package org.example.reserveit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.reserveit.models.Theatre;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TheatreDto {
    private Long id;
    private String name;
    private String address;
    private List<ScreenDto> screens;

    public static TheatreDto from(Theatre theatre) {

        List<ScreenDto> screens =
                theatre.getScreens() != null ? theatre.getScreens().stream()
                        .map(ScreenDto::fromWithoutTheatre).toList() : null;
        return TheatreDto.builder()
                .id(theatre.getId())
                .name(theatre.getName())
                .address(theatre.getAddress())
                .screens(screens)
                .build();
    }

    public static List<TheatreDto> from(List<Theatre> theatres) {
        return theatres.stream().map(TheatreDto::from).toList();
    }

    public static TheatreDto fromWithoutScreen(Theatre theatre) {
        return TheatreDto.builder()
                .id(theatre.getId())
                .name(theatre.getName())
                .address(theatre.getAddress())
                .build();
    }

    public static TheatreDto fromMinimal(Theatre theatre) {
        return TheatreDto.builder()
                .id(theatre.getId())
                .name(theatre.getName())
                .address(theatre.getAddress())
                .build();
    }

    public static List<TheatreDto> fromMinimal(List<Theatre> theatres) {
        return theatres.stream().map(TheatreDto::fromMinimal).toList();
    }
}
