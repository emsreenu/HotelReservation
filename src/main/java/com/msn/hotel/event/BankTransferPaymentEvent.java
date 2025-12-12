package com.msn.hotel.event;

import lombok.Data;

@Data
public class BankTransferPaymentEvent {
	 private String paymentId;
	    private String debtorAccountNumber;
	    private double amountReceived;
	    private String transactionDescription;

}
