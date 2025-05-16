package org.example.reserveit.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.reserveit.models.*;

import java.util.List;

@Getter
@Setter
public class TicketDto {
    private long id;
    private List<Seat> seats;
    private Show show;
    private TicketStatus status;
    private Payment payment;

    public static TicketDto from(Ticket ticket) {
        TicketDto dto = new TicketDto();
        dto.setId(ticket.getId());;
        dto.setSeats(ticket.getSeats());
        dto.setStatus(ticket.getStatus());
        dto.setShow(ticket.getShow());
        dto.setPayment(ticket.getPayment());

        return dto;
    }

}
