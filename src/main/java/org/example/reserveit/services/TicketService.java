package org.example.reserveit.services;

import org.example.reserveit.exceptions.*;
import org.example.reserveit.models.Ticket;

import java.util.List;

public interface TicketService {
    Ticket bookTicket(List<Long> showSeatIds, long userId)
            throws UserNotFoundException, ShowSeatNotFoundException, ShowSeatNotAvailableException;
    Ticket getTicket(Long id) throws TicketNotFoundException;
    Ticket cancelBooking(Long id) throws BookingNotFoundException;
}
