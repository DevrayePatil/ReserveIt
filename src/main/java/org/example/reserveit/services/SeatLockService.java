package org.example.reserveit.services;

import org.example.reserveit.models.ShowSeat;

import java.util.List;

public interface SeatLockService {
    void lock(Long userId, List<ShowSeat> showSeats);
    void validateLock(Long userId, List<ShowSeat> showSeats);
    void release(List<ShowSeat> showSeats);
}
