package org.example.reserveit.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "show_seat_types")
public class ShowSeatType extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;
    @Enumerated(EnumType.STRING)
    private SeatType seatType;
    private double price;
}
