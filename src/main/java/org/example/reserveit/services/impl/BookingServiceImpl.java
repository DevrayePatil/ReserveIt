package org.example.reserveit.services.impl;

import lombok.AllArgsConstructor;
import org.example.reserveit.exceptions.*;
import org.example.reserveit.models.*;
import org.example.reserveit.repositories.BookingRepository;
import org.example.reserveit.repositories.ShowSeatRepository;
import org.example.reserveit.repositories.ShowSeatTypeRepository;
import org.example.reserveit.repositories.UserRepository;
import org.example.reserveit.services.BookingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final ShowSeatRepository showSeatRepository;
    private final BookingRepository bookingRepository;
    private final ShowSeatTypeRepository showSeatTypeRepository;

    @Override
    public Booking createBooking(Long userId, List<Long> showSeatIds)
            throws UserNotFoundException, ShowSeatNotFoundException, ShowSeatNotAvailableException {



        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (showSeatIds == null || showSeatIds.isEmpty()) {
            throw new AttributeNotFoundException("showSeatIds");
        }

        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        if (showSeatIds.size() != showSeats.size()) {
            Set<Long> foundShowSeatIds = showSeats.stream()
                    .map(ShowSeat::getId).collect(Collectors.toSet());

            List<Long> notFoundShowSeatIds = showSeatIds.stream()
                    .filter(id -> !foundShowSeatIds.contains(id)).toList();

            if (!notFoundShowSeatIds.isEmpty()) {
                throw new ShowSeatNotFoundException(notFoundShowSeatIds);

            }
        }

        Show show = showSeats.get(0).getShow();

        boolean isSameShow = showSeats.stream().
                allMatch(showSeat -> showSeat.getShow().getId().equals(show.getId()));

        if (!isSameShow) {
            throw new IllegalArgumentException("All seats should be belongs to same show");
        }

        Map<SeatType, Double> seatTypePrice = showSeatTypeRepository
                .findAllByShow(show).stream()
                .collect(Collectors.toMap(ShowSeatType::getSeatType, ShowSeatType::getPrice));


        List<Long> unavailableSeats = showSeats.stream()
                .filter(showSeat -> !showSeat.getStatus().equals(ShowSeatStatus.AVAILABLE))
                .map(ShowSeat::getId)
                .toList();

        if (!unavailableSeats.isEmpty()) {
            throw new ShowSeatNotAvailableException(unavailableSeats);
        }

        double amount = 0;

        for (ShowSeat showSeat: showSeats) {

            Seat seat = showSeat.getSeat();
            SeatType type = seat.getType();

            Double price = seatTypePrice.get(type);

            if (price == null) {
                throw new PriceNotFountException(type);
            }

            amount += price;
            showSeat.setStatus(ShowSeatStatus.BLOCKED);

        }

        showSeatRepository.saveAll(showSeats);


        Booking booking = new Booking();

        booking.setShow(show);
        booking.setBookedBy(user);
        booking.setShowSeats(showSeats);
        booking.setAmount(amount);
        booking.setStatus(BookingStatus.PENDING);
        booking.setPaymentStatus(PaymentStatus.PENDING);

        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBooking(Long id) throws BookingNotFoundException {

        return bookingRepository.findById(id)
                .orElseThrow(BookingNotFoundException::new);
    }

    @Override
    public Booking cancelBooking(Long id) throws BookingNotFoundException {
        return null;
    }

    @Override
    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }
}
