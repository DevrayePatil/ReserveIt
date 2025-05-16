package org.example.reserveit.dto.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class BookingRequest {
    private Long userId;
    private List<Long> showSeatIds;
}
