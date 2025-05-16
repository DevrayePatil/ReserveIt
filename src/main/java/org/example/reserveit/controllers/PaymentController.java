package org.example.reserveit.controllers;

import com.stripe.exception.StripeException;

import jakarta.servlet.http.HttpServletRequest;

import org.example.reserveit.dto.responses.ApiResponse;
import org.example.reserveit.dto.responses.PaymentLinkResponse;
import org.example.reserveit.exceptions.BookingAlreadyPaidException;
import org.example.reserveit.exceptions.BookingNotFoundException;
import org.example.reserveit.services.PaymentService;

import org.example.reserveit.utils.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<PaymentLinkResponse>> processPayment(@PathVariable("bookingId") Long bookingId)
            throws StripeException, BookingNotFoundException, BookingAlreadyPaidException {

        String paymentLink = paymentService.createSession(bookingId);

        ApiResponse<PaymentLinkResponse> response = ResponseBuilder.created("Payment link created",
                new PaymentLinkResponse(paymentLink));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> webhook(HttpServletRequest request) throws IOException {

        String payload = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        String signHeader = request.getHeader("Stripe-Signature");

       paymentService.webhook(payload, signHeader);
       return ResponseEntity.ok("webhook");
    }

    @GetMapping("/success")
    public ResponseEntity<ApiResponse<String>> success() {
        ApiResponse<String> response = ApiResponse.of(HttpStatus.OK, "Payment completed", null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cancel")
    public ResponseEntity<ApiResponse<String>> cancel() {
        ApiResponse<String> response = ApiResponse.of(HttpStatus.OK, "Payment failed", null);
        return ResponseEntity.ok(response);
    }
}
