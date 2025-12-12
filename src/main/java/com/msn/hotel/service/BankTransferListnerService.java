package com.msn.hotel.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msn.hotel.model.BankTransferPaymentEvent;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Service
//@RequiredArgsConstructor
//public class BankTransferListnerService {
//	
//	@Autowired
//    private  ReservationService service;
//    
//	private ObjectMapper mapper = new ObjectMapper();
//
//    @KafkaListener(topics = "bank-transfer-payment-update", groupId = "hotel")
//    public void receive(String message) throws Exception {
//
//       BankTransferPaymentEvent event =
//                mapper.readValue(message, com.msn.hotel.event.BankTransferPaymentEvent.class);
//
//        String reservationId = extractReservationId(event.getTransactionDescription());
//
//        service.confirmFromBankEvent(reservationId, event.getPaymentId());
//    }
//
//    private String extractReservationId(String description) {
//        return description.substring(description.length() - 8);
//    }
//}
@Component
public class BankTransferListnerService {
    private final KafkaListenerEndpointRegistry registry;
    private  ReservationService service;
    private ObjectMapper mapper = new ObjectMapper();
    
    public BankTransferListnerService(KafkaListenerEndpointRegistry registry) {
        this.registry = registry;
    }
    
    @KafkaListener(topics = "bank-transfer-payment-update", groupId = "hotel")
	public void processMessage(String message) {
		System.out.println("Processing: " + message);
		try {
			BankTransferPaymentEvent event = mapper.readValue(message,
					com.msn.hotel.model.BankTransferPaymentEvent.class);
			String reservationId = extractReservationId(event.getTransactionDescription());

			service.confirmFromBankEvent(reservationId, event.getPaymentId());
			registry.getListenerContainer("timerListener").stop();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
    
//    @Scheduled(cron = "0 0/500 * * * ?") // Every 5 minutes
    @Scheduled(fixedDelay = 500)
    public void startListening() {
        registry.getListenerContainer("timerListener").start();
    }
    private String extractReservationId(String description) {
      return description.substring(description.length() - 8);
  }
}
