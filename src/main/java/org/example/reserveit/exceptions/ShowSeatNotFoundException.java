package org.example.reserveit.exceptions;

import java.util.List;

public class ShowSeatNotFoundException extends Exception {

    public ShowSeatNotFoundException() {
        super("ShowSeat not found");
    }

    public ShowSeatNotFoundException(String message) {
        super(message);
    }

    public ShowSeatNotFoundException(List<Long> ids) {
        super("Messing showSeat ids: " + ids);
    }
}
