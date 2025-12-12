package com.msn.hotel.model;

public class PaymentStatusResponse {
	 private String lastUpdateDate;   // ISO datetime string
	 private String status;           // e.g. CONFIRMED, REJECTED

	 public String getLastUpdateDate() {
	     return lastUpdateDate;
	 }

	 public void setLastUpdateDate(String lastUpdateDate) {
	     this.lastUpdateDate = lastUpdateDate;
	 }

	 public String getStatus() {
	     return status;
	 }

	 public void setStatus(String status) {
	     this.status = status;
	 }
}