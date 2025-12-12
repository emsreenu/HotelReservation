package com.hotel.hotel.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import com.msn.hotel.controller.ReservationController;
import com.msn.hotel.model.PaymentMode;
import com.msn.hotel.model.Reservation;
import com.msn.hotel.model.ReservationRequest;
import com.msn.hotel.model.ReservationStatus;
import com.msn.hotel.service.ReservationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReservation() {
        // Given: input request
        ReservationRequest request = new ReservationRequest();
        request.setCustomerName("John Doe");
        request.setPaymentMode(PaymentMode.CASH);
//        request.setStartDate("2025-01-01").setNights(2);

        // Given: mocked service response
        Reservation response = new Reservation();
        response.setId("101");
        response.setCustomerName("John Doe");
        response.setStatus(ReservationStatus.CONFIRMED);

        when(reservationService.create(any(ReservationRequest.class))).thenReturn(response);

        // When: calling controller method
        ResponseEntity<Reservation> result = reservationController.createReservation(request);
        System.out.println(result.getStatusCode());
        // Then: verify response
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        assertEquals(ReservationStatus.CONFIRMED, result.getBody().getStatus());
        assertEquals("John Doe", result.getBody().getCustomerName());

        // Verify interaction
        verify(reservationService, times(1)).create(any(ReservationRequest.class));
    }
}

