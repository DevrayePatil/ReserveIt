package org.example.reserveit.services.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.reserveit.exceptions.*;
import org.example.reserveit.models.*;
import org.example.reserveit.repositories.ScreenRepository;
import org.example.reserveit.repositories.SeatRepository;
import org.example.reserveit.repositories.TheatreRepository;
import org.example.reserveit.services.ScreenService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ScreenServiceImpl implements ScreenService {

    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;
    private final SeatRepository seatRepository;

    @Override
    public Screen addScreen(String name, Long theatreId, List<Feature> features,
                            List<Long> seatIds, ScreenStatus status)
            throws TheatreNotFoundException, SeatNotFoundException, AlreadyAssignedSeatsException {

        if (theatreId == null) {
            throw new AttributeNotFoundException("Theatre id");
        }

        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(TheatreNotFoundException::new);

        if (screenRepository.existsScreenByNameAndTheatre(name, theatre)) {
            throw new DuplicateEntityException("Screen with same name already present in this theatre");
        }
        if (features == null || features.isEmpty()) {
            throw new MissingFeaturesException();
        }

        if (seatIds == null || seatIds.isEmpty()) {
            throw new SeatNotFoundException("Seat list must not be empty");
        }

        if (status == null) {
            throw new AttributeNotFoundException("status");
        }

        List<Seat> seats = seatRepository.findAllById(seatIds);

        if (seatIds.size() != seats.size()) {
            Set<Long> foundSeatIds = seats.stream().
                    map(Seat::getId).collect(Collectors.toSet());
            List<Long> notFoundSeatIds = seatIds.stream()
                    .filter(id -> !foundSeatIds.contains(id)).toList();
            throw new SeatNotFoundException(notFoundSeatIds);
        }

        List<Long> alreadyAssignedSeatIds = seats.stream()
                .filter(seat -> seat.getScreen() != null).map(Seat::getId).toList();

        if (!alreadyAssignedSeatIds.isEmpty()) {
            throw new AlreadyAssignedSeatsException(alreadyAssignedSeatIds);
        }

        Screen screen = new Screen();

        screen.setName(name);
        screen.setTheatre(theatre);
        screen.setFeatures(features);

        screen.setSeats(seats);
        screen.setStatus(status);

        screen = screenRepository.save(screen);

        for (Seat seat : seats) {
            seat.setScreen(screen);
        }
        seatRepository.saveAll(seats);

        return screen;
    }

    @Override
    public Screen getScreen(Long id) throws ScreenNotFoundException {
        return screenRepository.findById(id)
                .orElseThrow(ScreenNotFoundException::new);
    }


    @Transactional
    @Override
    public Screen updateScreen(Long id, String name, List<Feature> features,
                               List<Long> addSeatIds, List<Long> removeSeatIds, ScreenStatus status)
            throws ScreenNotFoundException, SeatNotFoundException, AlreadyAssignedSeatsException {

        Screen screen = screenRepository.findById(id)
                .orElseThrow(ScreenNotFoundException::new);

        if (name != null) screen.setName(name);

        if (features != null) screen.setFeatures(features);

        if (addSeatIds != null && !addSeatIds.isEmpty()) {

            List<Seat> seats = seatRepository.findAllById(addSeatIds);

            if (addSeatIds.size() != seats.size()) {
                Set<Long> foundSeatIds = seats.stream()
                        .map(Seat::getId).collect(Collectors.toSet());

                List<Long> notFoundSeatIds = addSeatIds.stream()
                        .filter(seatId -> !foundSeatIds.contains(seatId)).toList();

                throw new SeatNotFoundException(notFoundSeatIds);
            }

            List<Long> alreadyAssignedSeatIds = seats.stream()
                            .filter(seat -> seat.getScreen() != null
                                    && !seat.getScreen().getId().equals(screen.getId()))
                    .map(Seat::getId).toList();

            if (!alreadyAssignedSeatIds.isEmpty()) {
                throw new AlreadyAssignedSeatsException(alreadyAssignedSeatIds);
            }

            for (Seat seat: seats) {
                seat.setScreen(screen);
            }
        }

        if (removeSeatIds != null && !removeSeatIds.isEmpty()) {

            List<Seat> seatsToRemove = seatRepository.findAllById(removeSeatIds);

            if (removeSeatIds.size() != seatsToRemove.size()) {
                Set<Long> assignedToScreenSeatIds = seatsToRemove.stream()
                        .map(Seat::getId).collect(Collectors.toSet());

                List<Long> notAssignedToScreen = removeSeatIds.stream()
                        .filter(seatId -> !assignedToScreenSeatIds.contains(seatId)).toList();

                if (!notAssignedToScreen.isEmpty()) {
                    throw new SeatNotFoundException("Seats are not associated with the screen: " + notAssignedToScreen);
                }
            }


            List<Long> invalidSeats = seatsToRemove.stream()
                    .filter(seat -> seat.getScreen() == null
                            || !seat.getScreen().getId().equals(screen.getId()))
                    .map(Seat::getId).toList();

            if (!invalidSeats.isEmpty()) {
                throw new SeatNotFoundException("Seats not assigned to this screen: " + invalidSeats);
            }

            for (Seat seat : seatsToRemove) {
                seat.setScreen(null);
            }

        }

        if (status != null) screen.setStatus(status);

        return screenRepository.save(screen);
    }

    @Transactional
    @Override
    public void deleteScreen(Long id) throws ScreenNotFoundException {

        Screen screen = screenRepository.findById(id)
                .orElseThrow(ScreenNotFoundException::new);

        List<Seat> seats = seatRepository.findByScreen(screen);

        for (Seat seat: seats) {
            seat.setScreen(null);
        }

        seatRepository.saveAll(seats);

        screenRepository.delete(screen);

    }

    @Override
    public List<Screen> getScreensByTheatre(Long theatreId) throws TheatreNotFoundException {
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(TheatreNotFoundException::new);

        return screenRepository.findAllByTheatre(theatre);
    }

    @Override
    public List<Screen> getScreens() {
        return screenRepository.findAll();
    }
}
