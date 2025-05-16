package org.example.reserveit.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment extends BaseModel {

    @ManyToOne
    private Booking booking;
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private double amount;
    private Date paymentDate;
}
