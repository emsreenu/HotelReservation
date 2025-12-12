package com.msn.hotel.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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
	
	
	@Autowired
	private Environment env;
	private static final Logger log=LoggerFactory.getLogger(CreditCardPaymentClient.class);
    public PaymentStatusResponse getPaymentStatus(String paymentReference) {
    	String paymentURL=env.getProperty("app.paymenturl");
    	RestTemplate restTemplate= new RestTemplate();
        
    	
        log.info("Credit Card URL:"+paymentURL);
       
        PaymentStatusRetrievalRequest requestBody =
                new PaymentStatusRetrievalRequest(paymentReference);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentStatusRetrievalRequest> entity =
                new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<PaymentStatusResponse> response =
                    restTemplate.exchange(
                    		paymentURL,
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

