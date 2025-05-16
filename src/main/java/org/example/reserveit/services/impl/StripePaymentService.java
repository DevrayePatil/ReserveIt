package org.example.reserveit.services.impl;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;

import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.example.reserveit.exceptions.AttributeNotFoundException;
import org.example.reserveit.exceptions.BookingAlreadyPaidException;
import org.example.reserveit.exceptions.BookingNotFoundException;
import org.example.reserveit.models.*;
import org.example.reserveit.repositories.BookingRepository;
import org.example.reserveit.repositories.PaymentRepository;
import org.example.reserveit.repositories.ShowSeatRepository;
import org.example.reserveit.services.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StripePaymentService implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final Logger logger = LoggerFactory.getLogger(StripePaymentService.class);
    private final BookingRepository bookingRepository;
    private final ShowSeatRepository showSeatRepository;

    @Value("${stripe.api-key}")
    private String stripApiKey;
    @Value("${server.url}")
    private String url;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    public StripePaymentService(PaymentRepository paymentRepository, BookingRepository bookingRepository, ShowSeatRepository showSeatRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.showSeatRepository = showSeatRepository;
    }

    @Override
    public String createSession(Long bookingId) throws StripeException, BookingNotFoundException, BookingAlreadyPaidException {
        Stripe.apiKey = stripApiKey;

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(BookingNotFoundException::new);

        Double amount = booking.getAmount();

        if (booking.getPaymentStatus() == PaymentStatus.COMPLETED) {
            throw new BookingAlreadyPaidException();
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setClientReferenceId(bookingId.toString())
                .setSuccessUrl(url + "/payments/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(url + "/payments/cancel?session_id={CHECKOUT_SESSION_ID}")
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("inr")
                                                .setUnitAmount((long)(amount * 100)) // Convert to paise
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Booking #" + bookingId)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .putMetadata("bookingId", bookingId.toString())
                .build();

        Session session = Session.create(params);
        logger.info("Created Stripe session {} for booking {}", session.getId(), bookingId);
        return session.getUrl();
    }

    @Override
    public void webhook(String payload, String signHeader) {
        try {
            Event event = Webhook.constructEvent(payload, signHeader, webhookSecret);

            logger.info("Processing event: {}", event.getType());

            if ("checkout.session.completed".equals(event.getType())) {
                paymentCompleted(event);
            }
            if ("checkout.session.expired".equals(event.getType())) {
                expiredSession(event);
            }
        } catch (SignatureVerificationException e) {
            logger.error("Invalid webhook signature", e);
            throw new RuntimeException("Invalid webhook signature");
        } catch (Exception e) {
            logger.error("Webhook processing error", e);
            throw new RuntimeException("Webhook processing failed");
        }
    }

    private void expiredSession(Event event) {

        Session session = (Session) event.getDataObjectDeserializer().getObject()
                .orElseThrow(() -> new AttributeNotFoundException("session"));

        String referenceId = session.getClientReferenceId();

        if (referenceId == null) {
            throw new AttributeNotFoundException("bookingId");
        }

        Long bookingId = Long.valueOf(session.getMetadata().get( "bookingId"));
        Booking booking = bookingRepository.findById(bookingId).orElse(null);

        assert booking != null;
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(session.getAmountTotal() / 100.0);
        payment.setPaymentDate(new Date());
        payment.setStatus(PaymentStatus.FAILED);

        paymentRepository.save(payment);
    }

    @Override
    public void paymentCompleted (Event event){
        try {

            Session session = (Session) event.getDataObjectDeserializer().getObject()
                    .orElseThrow(() -> new AttributeNotFoundException("session"));


            String paymentIntentId = session.getPaymentIntent();
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

            String paymentMethodId = paymentIntent.getPaymentMethod();
            PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodId);

            String paymentType = paymentMethod.getType();

            String referenceId = session.getClientReferenceId();

            if (referenceId == null) {
                referenceId = session.getMetadata().get("bookingId");
            }

            if (referenceId == null) {
                logger.error("No booking ID found in session {}", session.getId());
                return;
            }

            logger.info("Payment type: {}", paymentType);

            Long bookingId = Long.parseLong(referenceId);

            Booking booking = bookingRepository.findById(bookingId)
                    .orElseThrow(BookingNotFoundException::new);

            List<ShowSeat> showSeats = booking.getShowSeats();

            for (ShowSeat showSeat: showSeats) {
                showSeat.setStatus(ShowSeatStatus.BOOKED);
            }
            showSeatRepository.saveAll(showSeats);

            Payment payment = new Payment();
            payment.setBooking(booking);
            try {
                payment.setPaymentMode(PaymentMode.valueOf(paymentType.toUpperCase()));
            } catch (IllegalArgumentException e) {
                logger.warn("Unknown payment mode: {}", paymentType);
                payment.setPaymentMode(PaymentMode.OTHER);
            }            payment.setAmount(session.getAmountTotal() / 100.0);
            payment.setPaymentDate(new Date());
            payment.setStatus(PaymentStatus.COMPLETED);

            paymentRepository.save(payment);

            booking.setPaymentStatus(PaymentStatus.COMPLETED);
            booking.setStatus(BookingStatus.COMPLETED);

            bookingRepository.save(booking);
            logger.info("Payment processed for booking {}", bookingId);

        } catch (Exception e) {
            logger.error("Error processing completed session", e);
        }
    }

    @Override
    public void paymentRefunded(Event event) {

    }
}
