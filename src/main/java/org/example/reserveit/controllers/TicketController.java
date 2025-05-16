//package org.example.reserveit.controllers;
//
//import lombok.AllArgsConstructor;
//import org.example.reserveit.dto.responses.ApiResponse;
//import org.example.reserveit.dto.ticket.BookTicketRequest;
//import org.example.reserveit.dto.TicketDto;
//import org.example.reserveit.exceptions.ShowSeatNotAvailableException;
//import org.example.reserveit.exceptions.ShowSeatNotFoundException;
//import org.example.reserveit.exceptions.UserNotFoundException;
//import org.example.reserveit.models.Ticket;
//import org.example.reserveit.services.TicketService;
//import org.example.reserveit.utils.ResponseBuilder;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@AllArgsConstructor
//@RestController
//@RequestMapping(("/tickets"))
//public class TicketController {
//
//    private final TicketService ticketService;
//
//    @PostMapping
//    public ResponseEntity<ApiResponse<TicketDto>> bookTicket(@RequestBody BookTicketRequest request)
//            throws UserNotFoundException, ShowSeatNotFoundException, ShowSeatNotAvailableException {
//
//        Ticket ticket = ticketService.bookTicket(request.getShowSeatIds(), request.getUserId());
//
//        TicketDto ticketDto = TicketDto.from(ticket);
//
//        ApiResponse<TicketDto> response = ResponseBuilder.ok("Ticket Booked Successfully", ticketDto);
//
//        return ResponseEntity.ok(response);
//
//    }
//}
