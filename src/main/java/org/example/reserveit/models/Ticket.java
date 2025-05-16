package org.example.reserveit.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tickets")

public class Ticket extends BaseModel {
    @ManyToOne
    private User bookedBy;
    @OneToMany
    private List<Seat> seats;
    @ManyToOne
    private Show show;
    @Enumerated(value = EnumType.STRING)
    private TicketStatus status;
    @OneToOne
    private Payment payment;
}
