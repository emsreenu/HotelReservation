package com.msn.hotel.controller;


import com.msn.hotel.model.Reservation;
import com.msn.hotel.model.ReservationRequest;
import com.msn.hotel.service.ReservationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
	@Autowired
    private ReservationService service;
	
	private static final Logger log = LoggerFactory.getLogger(ReservationController.class);

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody ReservationRequest reservation) {
    	log.info("Request Controller:createReservation:"+reservation.getCustomerName() );
    	Reservation reservationResponse =service.create(reservation);
    	log.info("Response Controller:createReservation:"+reservationResponse.getStatus());
    	return new ResponseEntity<>(reservationResponse, HttpStatus.ACCEPTED);
    	
    	 
    }
}
