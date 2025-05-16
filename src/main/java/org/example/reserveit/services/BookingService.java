package org.example.reserveit.services;

import org.example.reserveit.exceptions.BookingNotFoundException;
import org.example.reserveit.exceptions.ShowSeatNotAvailableException;
import org.example.reserveit.exceptions.ShowSeatNotFoundException;
import org.example.reserveit.exceptions.UserNotFoundException;
import org.example.reserveit.models.Booking;

import java.util.List;

public interface BookingService {

    Booking createBooking(Long userId, List<Long> showSeatIds)
            throws UserNotFoundException, ShowSeatNotFoundException, ShowSeatNotAvailableException;
    Booking getBooking(Long id) throws BookingNotFoundException;
    Booking cancelBooking(Long id) throws BookingNotFoundException;
    List<Booking> getBookings();
}
