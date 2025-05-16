package org.example.reserveit.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.reserveit.models.Booking;
import org.example.reserveit.models.BookingStatus;
import org.example.reserveit.models.PaymentStatus;

import java.util.List;

@Getter
@Setter
@Builder
public class BookingDto {
    private Long id;
    private ShowDto show;
    private String bookedBy;
    private List<ShowSeatDto> showSeats;
    private BookingStatus status;
    private Double amount;
    private PaymentStatus paymentStatus;

    public static BookingDto from(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .show(ShowDto.from(booking.getShow()))
                .bookedBy(booking.getBookedBy().getName())
                .showSeats(ShowSeatDto.fromMinimal(booking.getShowSeats()))
                .status(booking.getStatus())
                .amount(booking.getAmount())
                .paymentStatus(booking.getPaymentStatus())
                .build();
    }

    public static List<BookingDto> from(List<Booking> bookings) {
        return bookings.stream().map(BookingDto::from).toList();
    }
}
