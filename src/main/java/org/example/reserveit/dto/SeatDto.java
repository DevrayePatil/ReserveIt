package org.example.reserveit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.reserveit.models.Seat;
import org.example.reserveit.models.SeatType;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeatDto {
    private Long id;
    private String name;
    private SeatType type;
    private ScreenDto screen;

    public static SeatDto from(Seat seat) {

        ScreenDto nullableScreen =
                seat.getScreen() != null ? ScreenDto.fromMinimal(seat.getScreen()) : null;

        return SeatDto.builder()
                .id(seat.getId())
                .name(seat.getName())
                .type(seat.getType())
                .screen(nullableScreen)
                .build();
    }

    public static List<SeatDto> from(List<Seat> seats) {
        return seats.stream().map(SeatDto::from).toList();
    }

    public static SeatDto fromMinimal(Seat seat) {
        return SeatDto.builder()
                .id(seat.getId())
                .name(seat.getName())
                .type(seat.getType())
                .build();
    }

    public static List<SeatDto> fromMinimal(List<Seat> seats) {
        return seats.stream().map(SeatDto::fromMinimal).toList();
    }
}
