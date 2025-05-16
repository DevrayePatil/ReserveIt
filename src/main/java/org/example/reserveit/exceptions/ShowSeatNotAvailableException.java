package org.example.reserveit.exceptions;

import java.util.List;

public class ShowSeatNotAvailableException extends Exception {

    public ShowSeatNotAvailableException() {
        super("ShowSeat not available");
    }
    public ShowSeatNotAvailableException(String message) {
        super(message);
    }

    public ShowSeatNotAvailableException(List<Long> unavailableSeatIds) {
        super("ShowSeats are not available: " + unavailableSeatIds);
    }
}
