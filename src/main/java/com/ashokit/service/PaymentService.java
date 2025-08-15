package com.ashokit.service;

import com.ashokit.dtos.PaymentCallBackRequest;
import com.ashokit.dtos.PaymentResponseDto;

public interface PaymentService {
	 PaymentResponseDto createRazorpayOrder(Long rentalOrderId);
	    String handlePaymentCallback(PaymentCallBackRequest callbackDto);
}
