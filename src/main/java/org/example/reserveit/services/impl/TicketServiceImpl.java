package org.example.reserveit.services.impl;

import lombok.AllArgsConstructor;
import org.example.reserveit.exceptions.*;
import org.example.reserveit.models.*;
import org.example.reserveit.repositories.ShowSeatRepository;
import org.example.reserveit.repositories.TicketRepository;
import org.example.reserveit.repositories.UserRepository;
import org.example.reserveit.services.TicketService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class TicketServiceImpl implements TicketService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final ShowSeatRepository showSeatRepository;

    @Override
    public Ticket bookTicket(List<Long> showSeatIds, long userId)
            throws UserNotFoundException, ShowSeatNotFoundException, ShowSeatNotAvailableException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        if (showSeats.size() != showSeatIds.size()) {
            throw new ShowSeatNotFoundException("Show eats not found");
        }

        showSeats.stream().map(ShowSeat::getShow).findFirst()
                .orElseThrow(() ->new ShowSeatNotFoundException("Show Not Found"));

        List<ShowSeat> bookedSeats = new ArrayList<>();

        for (ShowSeat showSeat: showSeats) {
            if (!showSeat.getStatus().equals(ShowSeatStatus.AVAILABLE)) {
                bookedSeats.add(showSeat);
            }
        }

        if (!bookedSeats.isEmpty()) {
            throw new ShowSeatNotAvailableException("Seats " + bookedSeats + " are not available");
        }

        showSeats.forEach(showSeat -> showSeat.setStatus(ShowSeatStatus.BLOCKED));

        showSeatRepository.saveAll(showSeats);


        Ticket ticket = new Ticket();

        ticket.setBookedBy(user);
        ticket.setSeats(showSeats.stream().map(ShowSeat::getSeat).toList());
        ticket.setStatus(TicketStatus.UNPAID);
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket getTicket(Long id) throws TicketNotFoundException {
        return null;
    }

    @Override
    public Ticket cancelBooking(Long id) throws BookingNotFoundException {
        return null;
    }
}

