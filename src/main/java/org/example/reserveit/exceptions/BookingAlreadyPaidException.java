package org.example.reserveit.exceptions;

public class BookingAlreadyPaidException extends Exception {
    public BookingAlreadyPaidException() {
        super("This booking has already been paid.");
    }
}
