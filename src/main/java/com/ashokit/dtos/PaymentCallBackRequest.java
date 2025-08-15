package com.ashokit.dtos;

import com.ashokit.model.PaymentStatus;

import lombok.Data;

@Data
public class PaymentCallBackRequest {
    private Long rentalOrderId;
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
    
    private PaymentStatus status;
}
