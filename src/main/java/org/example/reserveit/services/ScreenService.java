package org.example.reserveit.services;

import org.example.reserveit.exceptions.AlreadyAssignedSeatsException;
import org.example.reserveit.exceptions.ScreenNotFoundException;
import org.example.reserveit.exceptions.SeatNotFoundException;
import org.example.reserveit.exceptions.TheatreNotFoundException;
import org.example.reserveit.models.*;

import java.util.List;

public interface ScreenService {

    Screen addScreen(String name, Long theatreId,
                     List<Feature> features, List<Long> seatIds, ScreenStatus status)
            throws TheatreNotFoundException, SeatNotFoundException, AlreadyAssignedSeatsException;

    Screen getScreen(Long id) throws ScreenNotFoundException;


    Screen updateScreen(Long id, String name, List<Feature> features,
                        List<Long> addSeatIds, List<Long> removeSeatId, ScreenStatus status)
        throws ScreenNotFoundException, SeatNotFoundException, AlreadyAssignedSeatsException;

    void deleteScreen(Long id) throws ScreenNotFoundException;


    List<Screen> getScreensByTheatre(Long theatreId) throws TheatreNotFoundException;

    List<Screen> getScreens();
}
