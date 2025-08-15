package com.ashokit.serviceImpl;


import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ashokit.dtos.PaymentCallBackRequest;
import com.ashokit.dtos.PaymentResponseDto;
import com.ashokit.model.Payment;
import com.ashokit.model.PaymentStatus;
import com.ashokit.model.RentalOrder;
import com.ashokit.repository.PaymentRepository;
import com.ashokit.repository.RentalOrderRepository;
import com.ashokit.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;



@Service

  public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	private  RentalOrderRepository rentalOrderRepository;
	
	@Autowired
    private  PaymentRepository paymentRepository;

    @Value("${razorpay.key}")
    private String razorpayKey;

    @Value("${razorpay.secret}")
    private String razorpaySecret;

	@Override
	public PaymentResponseDto createRazorpayOrder(Long rentalOrderId) {
		
		 RentalOrder rentalOrder = rentalOrderRepository.findById(rentalOrderId)
	                .orElseThrow(() -> new RuntimeException("Rental order not found"));
             
		 try {
			 RazorpayClient client = new RazorpayClient(razorpayKey,razorpaySecret);
			 double amountInRupees = rentalOrder.getTotalAmount();
			 
			 int amountPaisa = (int)Math.round(amountInRupees*100);
			 
			 JSONObject orderRequest = new JSONObject();
			 orderRequest.put("amount",amountPaisa);
			 orderRequest.put("currency", "INR");
			 orderRequest.put("receipt", "orderReceiptId_"+rentalOrder.getId());
			 orderRequest.put("payment_capture",true);
			 
			 Order order = client.orders.create(orderRequest);
			 Payment payment = paymentRepository.findByRentalOrderId(rentalOrderId)
		                .orElse(new Payment());
			  payment.setRazorpayOrderId(order.get("id"));
			  payment.setAmount(amountInRupees);
			  payment.setStatus(PaymentStatus.PENDING);
			  payment.setPaymentDate(LocalDate.now());
			  payment.setRentalOrder(rentalOrder);
			  
			  paymentRepository.save(payment);
			  
			  PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
			  
			  paymentResponseDto.setAmount(amountInRupees);
			  paymentResponseDto.setRazorpayOrderId(order.get("id"));
			  paymentResponseDto.setCurrency("INR");
			  paymentResponseDto.setKey(razorpayKey);
			  return paymentResponseDto;
		 }
		 catch(Exception e) {
			 throw new RuntimeException("Failed to create Razorpay order: " + e.getMessage());
	        }
		 
				     
	}

	@Override
	public String handlePaymentCallback(PaymentCallBackRequest callbackDto) {
	    try {
	        Payment payment = paymentRepository.findByRazorpayOrderId(callbackDto.getRazorpayOrderId())
	            .orElseThrow(() -> new RuntimeException("Payment Not Found"));

	        String data = callbackDto.getRazorpayOrderId() + "|" + callbackDto.getRazorpayPaymentId();
	        String generatedSignature = hmacSha256(data, razorpaySecret);

	        boolean isValid = generatedSignature.trim().equals(callbackDto.getRazorpaySignature().trim());

	        if (isValid) {
	            payment.setStatus(PaymentStatus.SUCCESS);
	        } else {
	            payment.setStatus(PaymentStatus.FAILED);
	        }

	        payment.setRazorpayPaymentId(callbackDto.getRazorpayPaymentId());
	        payment.setRazorpaySignature(callbackDto.getRazorpaySignature());
	        payment.setPaymentDate(LocalDate.now());

	        paymentRepository.save(payment);

	        return isValid ? "Payment Verified and Successful" : "Payment Verification Failed";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Payment verification failed due to an error.";
	    }
	}

	
	public String hmacSha256(String data, String secret) {
	    try {
	        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
	        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
	        sha256_HMAC.init(secret_key);
	        byte[] hash = sha256_HMAC.doFinal(data.getBytes());
	        return Hex.encodeHexString(hash); // ✅ FIXED
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to calculate hmac-sha256", e);
	    }
	}

}
