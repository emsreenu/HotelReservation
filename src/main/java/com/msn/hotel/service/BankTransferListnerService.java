package com.msn.hotel.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msn.hotel.event.BankTransferPaymentEvent;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankTransferListnerService {
	
	@Autowired
    private  ReservationService service;
    
	private ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "bank-transfer-payment-update", groupId = "hotel")
    public void receive(String message) throws Exception {

       BankTransferPaymentEvent event =
                mapper.readValue(message, com.msn.hotel.event.BankTransferPaymentEvent.class);

        String reservationId = extractReservationId(event.getTransactionDescription());

        service.confirmFromBankEvent(reservationId, event.getPaymentId());
    }

    private String extractReservationId(String description) {
        return description.substring(description.length() - 8);
    }
}
