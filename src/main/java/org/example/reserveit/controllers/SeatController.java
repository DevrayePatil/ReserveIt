package org.example.reserveit.controllers;

import lombok.AllArgsConstructor;
import org.example.reserveit.dto.SeatDto;
import org.example.reserveit.dto.requests.CreateSeatRequest;
import org.example.reserveit.dto.requests.UpdateSeatRequest;
import org.example.reserveit.dto.responses.ApiResponse;
import org.example.reserveit.exceptions.SeatNotFoundException;
import org.example.reserveit.models.Seat;
import org.example.reserveit.services.SeatService;
import org.example.reserveit.utils.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    @PostMapping
    public ResponseEntity<ApiResponse<SeatDto>> post(@RequestBody CreateSeatRequest request) {

        Seat seat = seatService.createSeat(request.getName(), request.getType(), request.getScreen());

        SeatDto seatDto = SeatDto.from(seat);

        ApiResponse<SeatDto> response = ResponseBuilder.created("Seat create successfully", seatDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SeatDto>> getSeat(@PathVariable("id") Long id)
            throws SeatNotFoundException {

        Seat seat = seatService.getSeat(id);

        SeatDto seatDto = SeatDto.from(seat);

        ApiResponse<SeatDto> response = ResponseBuilder.ok("Seat fetched successfully", seatDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SeatDto>> updateSeat(
            @PathVariable("id") Long id,
            @RequestBody UpdateSeatRequest request
            ) throws SeatNotFoundException {

        Seat seat = seatService.updateSeat(id, request.getName(),
                request.getType(), request.getScreen());

        SeatDto seatDto = SeatDto.from(seat);

        ApiResponse<SeatDto> response = ResponseBuilder.ok("Seat updated successfully", seatDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<SeatDto>> deleteSeat(@PathVariable("id") Long id) throws SeatNotFoundException {

        Seat seat = seatService.deleteSeat(id);

        SeatDto seatDto = SeatDto.from(seat);

        ApiResponse<SeatDto> response = ResponseBuilder.ok("Seat deleted successfully", seatDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SeatDto>>> getSeats() {

        List<Seat> seats = seatService.getSeats();

        List<SeatDto> seatDtos = SeatDto.from(seats);

        ApiResponse<List<SeatDto>> response =ResponseBuilder.ok("Seats fetched successfully", seatDtos, seatDtos.size());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
