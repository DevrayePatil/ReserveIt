package org.example.reserveit.controllers;

import org.example.reserveit.dto.TheatreDto;
import org.example.reserveit.dto.requests.CreateTheatreRequest;
import org.example.reserveit.dto.requests.UpdateTheatreRequest;
import org.example.reserveit.dto.responses.ApiResponse;
import org.example.reserveit.exceptions.TheatreNotFoundException;
import org.example.reserveit.models.Theatre;
import org.example.reserveit.services.TheatreService;
import org.example.reserveit.utils.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("theatres")
public class TheatreController {

    private final TheatreService theatreService;

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TheatreDto>> addTheatre(@RequestBody CreateTheatreRequest request) {

        Theatre theatre = theatreService.addTheatre(request.getName(), request.getAddress());

        TheatreDto theatreDto = TheatreDto.from(theatre);

        ApiResponse<TheatreDto> response = ResponseBuilder.created("Theatre Created successfully", theatreDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TheatreDto>> getTheatre(@PathVariable("id") Long id)
            throws TheatreNotFoundException {

        Theatre theatre = theatreService.getTheatre(id);

        TheatreDto theatreDto = TheatreDto.from(theatre);

        ApiResponse<TheatreDto> response = ResponseBuilder.ok("Theatre fetched successfully", theatreDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TheatreDto>> updateTheatre(
            @PathVariable("id") Long id,
            @RequestBody UpdateTheatreRequest request
    ) throws TheatreNotFoundException {

        Theatre theatre = theatreService.updateTheatre(id, request.getName(), request.getAddress());

        TheatreDto theatreDto = TheatreDto.from(theatre);

        ApiResponse<TheatreDto> response = ResponseBuilder.ok("Theatre updated successfully", theatreDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<TheatreDto>> deleteTheatre(@PathVariable("id") Long id)
            throws TheatreNotFoundException {

        Theatre theatre = theatreService.deleteTheatre(id);

        TheatreDto theatreDto = TheatreDto.from(theatre);

        ApiResponse<TheatreDto> response = ResponseBuilder.ok("Theatre deleted successfully", theatreDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TheatreDto>>> getTheatres() {

        List<Theatre> theatres = theatreService.getTheatres();

        List<TheatreDto> theatreDtos = TheatreDto.fromMinimal(theatres);

        ApiResponse<List<TheatreDto>> response = ResponseBuilder.ok("Theatres fetched successfully", theatreDtos, theatreDtos.size());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
