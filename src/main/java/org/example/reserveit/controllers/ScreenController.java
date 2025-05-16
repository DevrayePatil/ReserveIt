package org.example.reserveit.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.reserveit.dto.ScreenDto;
import org.example.reserveit.dto.requests.ScreenRequest;
import org.example.reserveit.dto.requests.UpdateScreenRequest;
import org.example.reserveit.dto.responses.ApiResponse;
import org.example.reserveit.exceptions.AlreadyAssignedSeatsException;
import org.example.reserveit.exceptions.ScreenNotFoundException;
import org.example.reserveit.exceptions.SeatNotFoundException;
import org.example.reserveit.exceptions.TheatreNotFoundException;
import org.example.reserveit.models.Screen;
import org.example.reserveit.services.ScreenService;
import org.example.reserveit.utils.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/screens")
public class ScreenController {

    private final ScreenService screenService;

    @PostMapping
    public ResponseEntity<ApiResponse<ScreenDto>> addScreen(@RequestBody ScreenRequest request)
            throws AlreadyAssignedSeatsException, TheatreNotFoundException, SeatNotFoundException {

        log.info("ScreenRequest: {}", request);
        Screen screen = screenService.addScreen(request.getName(), request.getTheatreId(), request.getFeatures(),
                request.getSeatIds(), request.getStatus());

        ScreenDto screenDto = ScreenDto.from(screen);

        ApiResponse<ScreenDto> response = ResponseBuilder.created("Screens added successfully", screenDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ScreenDto>> getScreen(@PathVariable("id") Long id)
            throws ScreenNotFoundException {

        Screen screen = screenService.getScreen(id);

        ScreenDto screenDto = ScreenDto.from(screen);

        ApiResponse<ScreenDto> response = ResponseBuilder.ok("Screen fetched successfully", screenDto);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ScreenDto>> updateScreen(
            @PathVariable("id") Long id,
            @RequestBody UpdateScreenRequest request
    ) throws ScreenNotFoundException, AlreadyAssignedSeatsException, SeatNotFoundException {

        Screen screen = screenService.updateScreen(id, request.getName(), request.getFeatures(),
                request.getAddSeatIds(), request.getRemoveSeatIds(), request.getStatus());

        ScreenDto screenDto = ScreenDto.from(screen);

        ApiResponse<ScreenDto> response = ResponseBuilder.ok("Screen updated successfully", screenDto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ScreenDto>> deleteScreen(@PathVariable("id") Long id)
            throws ScreenNotFoundException {

        Screen screen = screenService.getScreen(id);

        ScreenDto screenDto = ScreenDto.from(screen);

        screenService.deleteScreen(id);

        ApiResponse<ScreenDto> response = ResponseBuilder.ok("Screen deleted successfully", screenDto);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ScreenDto>>> getScreens(
            @RequestParam(value = "theatreId", required = false) Long theatreId
    ) throws TheatreNotFoundException {

        List<Screen> screens;

        if (theatreId != null) {
            screens = screenService.getScreensByTheatre(theatreId);
        }
        else {
           screens = screenService.getScreens();
        }

        List<ScreenDto> screenDtos = ScreenDto.fromMinimal(screens);

        ApiResponse<List<ScreenDto>> response = ResponseBuilder.ok("Screens fetched successfully", screenDtos, screenDtos.size());

        return ResponseEntity.ok(response);
    }
}
