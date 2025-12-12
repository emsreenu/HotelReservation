package com.msn.hotel.model;

import java.time.LocalDate;

import com.msn.hotel.validation.ValidDateRange;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@ValidDateRange
public class ReservationRequest {


    
	
    @NotNull(message = "Customer name cannot be empty")
	private String customerName;
    @Min(value = 1, message = "At least 1 customer is required")
    @Max(value = 10, message = "Maximum 10 customers allowed")
    private int noOfCustomers;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    
    @NotNull(message = "startDate name cannot be empty")
    private LocalDate startDate;
    @NotNull(message = "endDate name cannot be empty")
    private LocalDate endDate;
    
      
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    private String paymentReference;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
//    @ValidDateRange
//    private String dateRangeValidator;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getNoOfCustomers() {
		return noOfCustomers;
	}

	public void setNoOfCustomers(int noOfCustomers) {
		this.noOfCustomers = noOfCustomers;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}
    
    
    
    


}
