package org.example.reserveit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.reserveit.models.SeatType;
import org.example.reserveit.models.ShowSeat;
import org.example.reserveit.models.ShowSeatStatus;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShowSeatDto {

    private Long id;
    private SeatDto seat;
    private ShowDto show;
    private ShowSeatStatus status;
    private SeatType seatType;

    public static ShowSeatDto from(ShowSeat showSeat) {
        return ShowSeatDto.builder()
                .id(showSeat.getId())
                .seat(SeatDto.from(showSeat.getSeat()))
                .show(ShowDto.from(showSeat.getShow()))
                .status(showSeat.getStatus())
                .seatType(showSeat.getSeat().getType())
                .build();
    }

    public static List<ShowSeatDto> from(List<ShowSeat> showSeats) {
        return showSeats.stream().map(ShowSeatDto::from).toList();
    }

    public static ShowSeatDto fromMinimal(ShowSeat showSeat) {
        return ShowSeatDto.builder()
                .id(showSeat.getId())
                .status(showSeat.getStatus())
                .seatType(showSeat.getSeat().getType())
                .build();
    }

    public static List<ShowSeatDto> fromMinimal(List<ShowSeat> showSeats) {
        return showSeats.stream().map(ShowSeatDto::fromMinimal).toList();
    }
}
