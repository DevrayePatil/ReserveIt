package org.example.reserveit.services.impl;

import lombok.AllArgsConstructor;
import org.example.reserveit.exceptions.AttributeNotFoundException;
import org.example.reserveit.exceptions.SeatNotFoundException;
import org.example.reserveit.models.Screen;
import org.example.reserveit.models.Seat;
import org.example.reserveit.models.SeatType;
import org.example.reserveit.repositories.SeatRepository;
import org.example.reserveit.services.SeatService;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    @Override
    public Seat createSeat(String name, SeatType type, Screen screen) {


        if (name == null || name.isEmpty()) {
            throw new AttributeNotFoundException("Seat name");
        }

        Seat seat = new Seat();

        seat.setName(name);
        seat.setType(type);
        seat.setScreen(screen);

        return seatRepository.save(seat);
    }

    @Override
    public Seat getSeat(Long id) throws SeatNotFoundException {
        return seatRepository.findById(id)
                .orElseThrow(SeatNotFoundException::new);
    }

    @Override
    public Seat updateSeat(Long id, String name, SeatType type, Screen screen)
            throws SeatNotFoundException {

        Seat seat = seatRepository.findById(id)
                .orElseThrow(SeatNotFoundException::new);

        if (name != null && !name.isEmpty()) seat.setName(name);

        if (type != null) seat.setType(type);

        if (screen != null)seat.setScreen(screen);

        return seatRepository.save(seat);
    }

    @Override
    public Seat deleteSeat(Long id) throws SeatNotFoundException {

        Seat seat = seatRepository.findById(id)
                .orElseThrow(SeatNotFoundException::new);
        seatRepository.delete(seat);

        return seat;
    }

    @Override
    public List<Seat> getSeats() {
        return seatRepository.findAll();
    }
}
