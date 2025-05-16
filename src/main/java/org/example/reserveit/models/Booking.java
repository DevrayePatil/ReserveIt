package org.example.reserveit.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking extends BaseModel {
    @ManyToOne
    private Show show;
    @ManyToOne
    private User bookedBy;
    @OneToMany(mappedBy = "booking")
    private List<ShowSeat> showSeats;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;


}
