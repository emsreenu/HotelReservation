package com.msn.hotel.service;

import com.msn.hotel.client.CreditCardPaymentClient;
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
    private CreditCardPaymentClient creditCardClient;

    public Reservation create(ReservationRequest reservation) {

        reservation.setStatus(
                reservation.getPaymentMode() == PaymentMode.CASH
                        ? ReservationStatus.CONFIRMED
                        : ReservationStatus.PENDING_PAYMENT
        );
    	Reservation response=this.saveReservation(reservation);
        

        // CREDIT CARD → call external service
    	
        if (reservation.getPaymentMode() == PaymentMode.CREDIT_CARD) {

            PaymentStatusResponse paymentStatusResponse = creditCardClient.getPaymentStatus(response.getId());

            if (paymentStatusResponse.getStatus().equals("CONFIRMED")) {
            	reservation.setStatus(ReservationStatus.CONFIRMED);
            	response= this.saveReservation(reservation);
            }
        }

        // BANK TRANSFER → wait for Kafka event
        return response;
    }

    private Reservation saveReservation(ReservationRequest reservation) {
		// TODO Auto-generated method stub
    	Reservation reservationEntity = new Reservation();
    	reservationEntity.setCustomerName(reservation.getCustomerName());
    	reservationEntity.setStartDate(reservation.getStartDate());
    	reservationEntity.setEndDate(reservation.getEndDate());
    	reservationEntity.setNoOfCustomers(reservation.getNoOfCustomers());
    	reservationEntity.setPaymentMode(reservation.getPaymentMode());
    	reservationEntity.setRoomType(reservation.getRoomType());
    	reservationEntity.setStatus(reservation.getStatus());
    	Reservation saved = repo.save(reservationEntity);
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
