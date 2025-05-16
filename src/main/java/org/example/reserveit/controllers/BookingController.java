package org.example.reserveit.controllers;

import lombok.AllArgsConstructor;
import org.example.reserveit.dto.BookingDto;
import org.example.reserveit.dto.requests.BookingRequest;
import org.example.reserveit.dto.responses.ApiResponse;
import org.example.reserveit.exceptions.BookingNotFoundException;
import org.example.reserveit.exceptions.ShowSeatNotAvailableException;
import org.example.reserveit.exceptions.ShowSeatNotFoundException;
import org.example.reserveit.exceptions.UserNotFoundException;
import org.example.reserveit.models.Booking;
import org.example.reserveit.services.BookingService;
import org.example.reserveit.utils.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {


    private final BookingService bookingService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingDto>> getBooking(@PathVariable("id") Long id) throws BookingNotFoundException {

        Booking booking = bookingService.getBooking(id);

        BookingDto bookingDto = BookingDto.from(booking);

        ApiResponse<BookingDto> response = ResponseBuilder.ok("Booking fetched successfully", bookingDto);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookingDto>> createBooking(@RequestBody BookingRequest request)
            throws UserNotFoundException, ShowSeatNotFoundException, ShowSeatNotAvailableException {

        Booking booking = bookingService.createBooking(request.getUserId(), request.getShowSeatIds());

        BookingDto bookingDto = BookingDto.from(booking);

        ApiResponse<BookingDto> response = ResponseBuilder.created("Booking created successfully", bookingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingDto>> updateBooking(@PathVariable("id") Long id) throws BookingNotFoundException {

        Booking booking = bookingService.cancelBooking(id);

        BookingDto bookingDto = BookingDto.from(booking);

        ApiResponse<BookingDto> response = ResponseBuilder.ok("Booking cancelled successfully", bookingDto);

        return ResponseEntity.ok(response);
    }


    @GetMapping()
    public ResponseEntity<ApiResponse<List<BookingDto>>> getBookings() {

        List<Booking> bookings = bookingService.getBookings();

        List<BookingDto> bookingDtos = BookingDto.from(bookings);

        ApiResponse<List<BookingDto>> response = ResponseBuilder.ok("Bookings fetched successfully", bookingDtos, bookingDtos.size());

        return ResponseEntity.ok(response);
    }
}
