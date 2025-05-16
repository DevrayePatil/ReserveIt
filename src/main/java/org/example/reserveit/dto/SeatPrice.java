package org.example.reserveit.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.reserveit.models.SeatType;

@Getter
@Setter
public class SeatPrice {
    SeatType seatType;
    Double price;
}
