package org.example.reserveit.dto.requests;

import lombok.Getter;
import lombok.Setter;
import org.example.reserveit.models.Screen;
import org.example.reserveit.models.SeatType;

@Getter
@Setter
public class UpdateSeatRequest {
    private String name;
    private SeatType type;
    private Screen screen;
}
