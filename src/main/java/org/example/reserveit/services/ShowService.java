package org.example.reserveit.services;

import org.example.reserveit.dto.SeatPrice;
import org.example.reserveit.exceptions.*;
import org.example.reserveit.models.Feature;
import org.example.reserveit.models.Show;
import org.example.reserveit.models.ShowSeat;

import java.util.Date;
import java.util.List;

public interface ShowService {

    Show createShow(Long userId, Long movieId, Long screenId, Date startTime, Date endTime,
                    List<Feature> features, List<SeatPrice> pricingConfig)
        throws UserNotFoundException, MovieNotFoundException, ScreenNotFoundException, FeatureNotSupportedByScreenException,
            InvalidDateException, UnAuthorizedAccessException;

    Show getShow(Long id) throws ShowNotFoundException;

    Show updateShow(Long id, Long userId, Long movieId, Long screenId, Date startTime, Date endTime,
                    List<Feature> features, List<SeatPrice> pricingConfig)
        throws ShowNotFoundException, UserNotFoundException, MovieNotFoundException, ScreenNotFoundException,
            FeatureNotSupportedByScreenException, InvalidDateException, UnAuthorizedAccessException;

    Show deleteShow(Long id) throws ShowNotFoundException;

    List<ShowSeat> getShowSeats(Long id) throws ShowNotFoundException;
    List<Show> getShows();
}
