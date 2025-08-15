package com.ashokit.dtos;


import lombok.Data;

@Data

public class PaymentResponseDto {
    private String razorpayOrderId;
    private double amount;
    private String currency;
    private String key; 
}
