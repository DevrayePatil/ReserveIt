package org.example.reserveit.exceptions;

import java.util.List;

public class SeatNotFoundException extends Exception {

    public SeatNotFoundException() {
        super("Seat not found");
    }
    public SeatNotFoundException(String message) {
        super(message);
    }

    public SeatNotFoundException(List<Long> ids) {
        super("Some seats not found" + ids);
    }
}
