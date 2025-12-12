package com.msn.hotel.model;

public class PaymentStatusRetrievalRequest {

	private String paymentReference; // example: DL123456789

	public PaymentStatusRetrievalRequest() {
	}

	public PaymentStatusRetrievalRequest(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}
}