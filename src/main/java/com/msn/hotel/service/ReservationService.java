package com.msn.hotel.service;

import com.msn.hotel.model.*;
import com.msn.hotel.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ReservationService {

    @Autowired
	private ReservationRepository repo;
    @Autowired
    private CreditCardPaymentClient creditCardClient;

    public Reservation create(Reservation reservation) {

        long days = ChronoUnit.DAYS.between(reservation.getStartDate(), reservation.getEndDate());
        if (days > 30)
            throw new RuntimeException("Reservation cannot exceed 30 days.");

        reservation.setStatus(
                reservation.getPaymentMode() == PaymentMode.CASH
                        ? ReservationStatus.CONFIRMED
                        : ReservationStatus.PENDING_PAYMENT
        );

        Reservation saved = repo.save(reservation);

        // CREDIT CARD → call external service
        if (reservation.getPaymentMode() == PaymentMode.CREDIT_CARD) {

            PaymentStatusResponse paymentStatusResponse = creditCardClient.getPaymentStatus(saved.getId());

            if (paymentStatusResponse.getStatus().equals("CONFIRMED")) {
                saved.setStatus(ReservationStatus.CONFIRMED);
                return repo.save(saved);
            }
        }

        // BANK TRANSFER → wait for Kafka event
        return saved;
    }

    public Reservation confirmFromBankEvent(String reservationId, String paymentRef) {
        Reservation r = repo.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        r.setPaymentReference(paymentRef);
        r.setStatus(ReservationStatus.CONFIRMED);
        return repo.save(r);
    }
}
