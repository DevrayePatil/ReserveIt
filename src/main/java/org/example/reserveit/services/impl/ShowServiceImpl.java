package org.example.reserveit.services.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.reserveit.dto.SeatPrice;
import org.example.reserveit.exceptions.*;
import org.example.reserveit.models.*;
import org.example.reserveit.repositories.*;
import org.example.reserveit.services.ShowService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@AllArgsConstructor

@Service
public class ShowServiceImpl implements ShowService {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final ScreenRepository screenRepository;
    private final ShowSeatRepository showSeatRepository;
    private final ShowSeatTypeRepository showSeatTypeRepository;

    @Override
    public Show createShow(Long userId, Long movieId, Long screenId, Date startTime,
                           Date endTime, List<Feature> features, List<SeatPrice> pricingConfig)
            throws UserNotFoundException, MovieNotFoundException, ScreenNotFoundException,
            FeatureNotSupportedByScreenException, InvalidDateException, UnAuthorizedAccessException {

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (!user.getType().equals(UserType.ADMIN)) {
            throw new UnAuthorizedAccessException("User is not admin");
        }

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(MovieNotFoundException::new);

        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(ScreenNotFoundException::new);

        validateSupportedFeatures(screen, features);

        if (startTime.before(new Date())) {
            throw new InvalidDateException();
        }

        if (endTime.before(startTime)) {
            throw new InvalidDateException();
        }

        List<Show> shows = showRepository.findAllByScreen(screen);

        for (Show s : shows) {
            if (isOverLapping(s.getStartTime(), s.getEndTime(), startTime, endTime)) {
                throw new OverLappingException("Show is already running in this time period");
            }
        }

        Map<SeatType, Double> seatPrising = pricingConfig.stream()
                .collect(Collectors.toMap(SeatPrice::getSeatType, SeatPrice::getPrice));

        for (Seat seat : screen.getSeats()) {
            if (!seatPrising.containsKey(seat.getType())) {
                throw new IllegalArgumentException("Price is not configured for: " + seat.getType());
            }
        }

        Show show = new Show();

        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(startTime);
        show.setEndTime(endTime);
        show.setFeatures(features);

        showRepository.save(show);

        List<ShowSeat> showSeats = screen.getSeats().stream()
                .map(seat -> {
                    ShowSeat showSeat = new ShowSeat();
                    showSeat.setSeat(seat);
                    showSeat.setShow(show);
                    showSeat.setStatus(ShowSeatStatus.AVAILABLE);
                    return showSeat;
                }).toList();

        showSeatRepository.saveAll(showSeats);

        List<ShowSeatType> showSeatTypes = pricingConfig.stream()
                .map(seatPrice -> {
                    ShowSeatType showSeatType = new ShowSeatType();

                    showSeatType.setShow(show);
                    showSeatType.setSeatType(seatPrice.getSeatType());
                    showSeatType.setPrice(seatPrice.getPrice());
                    return showSeatType;
                }).toList();

        showSeatTypeRepository.saveAll(showSeatTypes);

        return show;
    }

    @Override
    public Show getShow(Long id) throws ShowNotFoundException {

        return showRepository.findById(id)
                .orElseThrow(ShowNotFoundException::new);
    }

    @Transactional
    @Override
    public Show updateShow(Long id, Long userId, Long movieId, Long screenId, Date startTime, Date endTime,
                           List<Feature> features, List<SeatPrice> pricingConfig)
            throws ShowNotFoundException, UserNotFoundException, MovieNotFoundException, ScreenNotFoundException,
            FeatureNotSupportedByScreenException, InvalidDateException, UnAuthorizedAccessException {

        if (userId == null) {
            throw new AttributeNotFoundException("userId");
        }
        Show show = showRepository.findById(id)
                .orElseThrow(ShowNotFoundException::new);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Screen screen = show.getScreen();

        if (!user.getType().equals(UserType.ADMIN)) {
            throw new UnAuthorizedAccessException();
        }

        if (movieId != null) {
            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(MovieNotFoundException::new);

            show.setMovie(movie);
        }

        if (screenId != null) {
            screen = screenRepository.findById(screenId)
                    .orElseThrow(ScreenNotFoundException::new);

            show.setScreen(screen);
        }


        Date updatedStartTime = startTime != null ? startTime : show.getStartTime();
        Date updatedEndTime = endTime != null ? endTime : show.getEndTime();

        if (updatedStartTime.before(new Date())) {
            throw new InvalidDateException();
        }

        if (updatedEndTime.before(updatedStartTime)) {
            throw new InvalidDateException();
        }

        show.setStartTime(updatedStartTime);
        show.setEndTime(updatedEndTime);


        List<Show> runningShows = showRepository.findAllByScreen(screen);


        for (Show s : runningShows) {
            boolean isOverlapping = isOverLapping(s.getStartTime(), s.getEndTime(),
                    show.getStartTime(), show.getEndTime());

            if (!s.getId().equals(show.getId()) && isOverlapping) {
                throw new OverLappingException("Show is already running in this time period");
            }
        }

        if (features != null && !features.isEmpty()) {
            validateSupportedFeatures(screen, features);
            show.setFeatures(features);
        }

        if (pricingConfig != null && !pricingConfig.isEmpty()) {

            Map<SeatType, Double> seatPricing = pricingConfig.stream()
                    .collect(Collectors.toMap(SeatPrice::getSeatType, SeatPrice::getPrice));

            for (Seat seat : screen.getSeats()) {
                if (!seatPricing.containsKey(seat.getType())) {
                    throw new IllegalArgumentException("Price is not configured for: " + seat.getType());
                }
            }

            showSeatRepository.deleteAllByShow(show);
            showSeatTypeRepository.deleteAllByShow(show);

            List<ShowSeat> showSeats = screen.getSeats().stream()
                    .map(seat -> {
                        ShowSeat showSeat = new ShowSeat();
                        showSeat.setSeat(seat);
                        showSeat.setShow(show);
                        showSeat.setStatus(ShowSeatStatus.AVAILABLE);
                        return showSeat;
                    }).toList();

            showSeatRepository.saveAll(showSeats);

            List<ShowSeatType> showSeatTypes = pricingConfig.stream()
                    .map(seatPrice -> {
                        ShowSeatType showSeatType = new ShowSeatType();

                        showSeatType.setShow(show);
                        showSeatType.setSeatType(seatPrice.getSeatType());
                        showSeatType.setPrice(seatPrice.getPrice());

                        return showSeatType;
                    }).toList();

            showSeatTypeRepository.saveAll(showSeatTypes);
        }

        return showRepository.save(show);
    }

    @Override
    public Show deleteShow(Long id) throws ShowNotFoundException {

        Show show = showRepository.findById(id)
                .orElseThrow(ShowNotFoundException::new);

        showSeatTypeRepository.deleteAllByShow(show);
        showSeatRepository.deleteAllByShow(show);
        return show;
    }

    @Override
    public List<ShowSeat> getShowSeats(Long id) throws ShowNotFoundException {
        Show show = showRepository.findById(id)
                .orElseThrow(ShowNotFoundException::new);
        return showSeatRepository.findAllByShow(show);
    }

    @Override
    public List<Show> getShows() {
        return showRepository.findAll();
    }


    private boolean isOverLapping(Date start, Date end, Date newStart, Date newEnd) {
        return newStart.before(end) && newEnd.after(start);
    }

    private void validateSupportedFeatures(Screen screen, List<Feature> features)
            throws FeatureNotSupportedByScreenException {
        Set<Feature> supportedFeatures = new HashSet<>(screen.getFeatures());

        List<Feature> unsupportedFeatures = features.stream()
                .filter(feature -> !supportedFeatures.contains(feature)).toList();

        if (!unsupportedFeatures.isEmpty()) {
            throw new FeatureNotSupportedByScreenException(unsupportedFeatures);
        }
    }
}
