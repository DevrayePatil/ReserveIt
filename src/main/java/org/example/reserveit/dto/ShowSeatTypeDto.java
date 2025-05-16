package org.example.reserveit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.reserveit.models.SeatType;
import org.example.reserveit.models.ShowSeatType;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShowSeatTypeDto {
    private Long id;
    private ShowDto show;
    private SeatType seatType;
    private Double price;


    public static ShowSeatTypeDto from(ShowSeatType seatType) {
        return ShowSeatTypeDto.builder()
                .id(seatType.getId())
                .show(ShowDto.from(seatType.getShow()))
                .seatType(seatType.getSeatType())
                .price(seatType.getPrice())
                .build();
    }

    public static List<ShowSeatTypeDto> from(List<ShowSeatType> seatTypes) {
        return seatTypes.stream().map(ShowSeatTypeDto::from).toList();
    }


    public static ShowSeatTypeDto fromMinimal(ShowSeatType seatType) {
        return ShowSeatTypeDto.builder()
                .seatType(seatType.getSeatType())
                .price(seatType.getPrice())
                .build();
    }

    public static List<ShowSeatTypeDto> fromMinimal(List<ShowSeatType> seatTypes) {
        return seatTypes.stream().map(ShowSeatTypeDto::fromMinimal).toList();
    }
}
