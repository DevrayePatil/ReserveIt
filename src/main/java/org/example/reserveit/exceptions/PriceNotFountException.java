package org.example.reserveit.exceptions;

import org.example.reserveit.models.SeatType;

public class PriceNotFountException extends RuntimeException {
    public PriceNotFountException(SeatType type) {
        super("Price not found for seat type: " + type);
    }
}
