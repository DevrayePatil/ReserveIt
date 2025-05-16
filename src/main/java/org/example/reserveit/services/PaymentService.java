package org.example.reserveit.services;

import com.stripe.exception.StripeException;
import org.example.reserveit.exceptions.BookingAlreadyPaidException;
import org.example.reserveit.exceptions.BookingNotFoundException;


public interface PaymentService {

    String createSession(Long bookingId)
            throws StripeException, BookingNotFoundException, BookingAlreadyPaidException;
    void webhook(String payload, String signHeader);

    void paymentCompleted(com.stripe.model.Event event) throws BookingNotFoundException;
    void paymentRefunded(com.stripe.model.Event event);
}
