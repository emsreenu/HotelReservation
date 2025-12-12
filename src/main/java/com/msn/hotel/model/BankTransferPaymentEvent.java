package com.msn.hotel.model;

import lombok.Getter;

@Getter
public class BankTransferPaymentEvent {
	 private String paymentId;
	    private String debtorAccountNumber;
	    private double amountReceived;
	    private String transactionDescription;

}
