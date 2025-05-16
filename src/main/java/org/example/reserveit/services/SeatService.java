package org.example.reserveit.services;

import org.example.reserveit.exceptions.SeatNotFoundException;
import org.example.reserveit.models.Screen;
import org.example.reserveit.models.Seat;
import org.example.reserveit.models.SeatType;

import java.util.List;

public interface SeatService {

    Seat createSeat(String name, SeatType type, Screen screen);
    Seat getSeat(Long id) throws SeatNotFoundException;
    Seat updateSeat(Long id, String name, SeatType type,  Screen screen) throws SeatNotFoundException;
    Seat deleteSeat(Long id) throws SeatNotFoundException;
    List<Seat> getSeats() ;

}
