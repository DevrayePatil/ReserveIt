package org.example.reserveit.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "show_seats")
public class ShowSeat extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
    @ManyToOne
    private Seat seat;
    @JoinColumn(name = "show_id")
    @ManyToOne
    private Show show;
    private ShowSeatStatus status;
}
