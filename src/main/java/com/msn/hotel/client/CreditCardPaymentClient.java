package com.msn.hotel.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msn.hotel.model.ErrorResponse;
import com.msn.hotel.model.PaymentStatusResponse;
import com.msn.hotel.model.PaymentStatusRetrievalRequest;

import lombok.RequiredArgsConstructor;

@Component
public class CreditCardPaymentClient {

     
    
    private static final String BASE_URL = "http://localhost:9000/host/credit-card-payment-api";


    public PaymentStatusResponse getPaymentStatus(String paymentReference) {
    	RestTemplate restTemplate= new RestTemplate();
        String url = BASE_URL + "/payment-status";

        PaymentStatusRetrievalRequest requestBody =
                new PaymentStatusRetrievalRequest(paymentReference);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentStatusRetrievalRequest> entity =
                new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<PaymentStatusResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            PaymentStatusResponse.class
                    );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            // for 400 / 404 / 500 the body matches ErrorResponse
            ErrorResponse error = null;
            try {
                error = new ObjectMapper()
                        .readValue(e.getResponseBodyAsString(), ErrorResponse.class);
            } catch (Exception ignored) {}
            throw new RuntimeException("Credit card API error: "
                    + (error != null ? error.getError() : e.getStatusText()));
        }
    }
}

