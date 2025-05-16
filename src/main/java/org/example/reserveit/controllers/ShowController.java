package org.example.reserveit.controllers;

import lombok.AllArgsConstructor;
import org.example.reserveit.dto.ShowDto;
import org.example.reserveit.dto.ShowSeatDto;
import org.example.reserveit.dto.requests.UpdateShowRequest;
import org.example.reserveit.dto.responses.ApiResponse;
import org.example.reserveit.dto.requests.CreateShowRequest;
import org.example.reserveit.exceptions.*;
import org.example.reserveit.models.Show;
import org.example.reserveit.models.ShowSeat;
import org.example.reserveit.services.ShowService;
import org.example.reserveit.utils.ResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowService showService;

    @PostMapping
    public ResponseEntity<ApiResponse<ShowDto>> createShow(@RequestBody CreateShowRequest request)
            throws UserNotFoundException, UnAuthorizedAccessException, InvalidDateException,
            ScreenNotFoundException, FeatureNotSupportedByScreenException, MovieNotFoundException {

        Show show = showService.createShow(request.getUserId(), request.getMovieId(), request.getScreenId(),
                request.getStartTime(), request.getEndTime(), request.getFeatures(), request.getPricingConfig());

        ShowDto showDto = ShowDto.from(show);

        ApiResponse<ShowDto> response = ResponseBuilder.created("Show created successfully", showDto);

        return ResponseEntity.status(CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ShowDto>> getShow(@PathVariable("id") Long id)
            throws ShowNotFoundException {

        Show show = showService.getShow(id);

        ShowDto showDto = ShowDto.from(show);

        ApiResponse<ShowDto> response = ResponseBuilder.ok("Show fetched successfully", showDto);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ShowDto>> updateShow(
            @PathVariable("id") Long id,
            @RequestBody UpdateShowRequest request
    ) throws UserNotFoundException, UnAuthorizedAccessException, ShowNotFoundException, InvalidDateException,
            ScreenNotFoundException, FeatureNotSupportedByScreenException, MovieNotFoundException {

        Show show = showService.updateShow(id, request.getUserId(), request.getMovieId(), request.getScreenId(),
                request.getStartTime(), request.getEndTime(), request.getFeatures(), request.getPricingConfig());

        ShowDto showDto = ShowDto.from(show);

        ApiResponse<ShowDto> response = ResponseBuilder.ok("Show updated successfully", showDto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ShowDto>> deleteShow(@PathVariable("id") Long id)
            throws ShowNotFoundException {

        Show show = showService.deleteShow(id);

        ShowDto showDto = ShowDto.from(show);

        ApiResponse<ShowDto> response = ResponseBuilder.ok("Show deleted successfully", showDto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/seats")
    public ResponseEntity<ApiResponse<List<ShowSeatDto>>> getShowSeats(@PathVariable("id") Long id)
            throws ShowNotFoundException {

        List<ShowSeat> showSeats = showService.getShowSeats(id);

        List<ShowSeatDto> showSeatDtos = ShowSeatDto.fromMinimal(showSeats);
        ApiResponse<List<ShowSeatDto>> response = ResponseBuilder.ok("Seats fetched successfully", showSeatDtos);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ShowDto>>> getShows() {

        List<Show> shows = showService.getShows();

        List<ShowDto> showDtos = ShowDto.from(shows);

        ApiResponse<List<ShowDto>> response = ResponseBuilder.ok("Shows fetched successfully", showDtos, showDtos.size());

        return ResponseEntity.ok(response);
    }
}
