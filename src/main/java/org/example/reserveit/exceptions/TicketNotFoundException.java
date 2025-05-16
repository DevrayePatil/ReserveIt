package org.example.reserveit.exceptions;

public class TicketNotFoundException extends Exception {
    public TicketNotFoundException() {
        super("Ticket not found");
    }
}
