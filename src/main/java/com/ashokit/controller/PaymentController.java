package com.ashokit.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ashokit.dtos.PaymentCallBackRequest;
import com.ashokit.dtos.PaymentResponseDto;
import com.ashokit.service.PaymentService;

@Controller
@RequestMapping("/razor")
public class PaymentController {

    @Value("${razorpay.key}")
    private String razorpayKey;

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

   
    @GetMapping("/payment-page")
    public String paymentPage(@RequestParam(name = "rentalOrderId", required = false, defaultValue = "1") Long rentalOrderId,
                              Model model) {
        model.addAttribute("razorpayKey", razorpayKey);
        model.addAttribute("rentalOrderId", rentalOrderId);
        return "razorpay-payment";  

    }
    @PostMapping("/payment/order/{rentalOrderId}")
    @ResponseBody
    public PaymentResponseDto createOrder(@PathVariable Long rentalOrderId) {
        return paymentService.createRazorpayOrder(rentalOrderId);
    }

   
    @PostMapping("/payment/callback")
    @ResponseBody
    public String handleCallback(@RequestBody PaymentCallBackRequest callbackDto) {
        return paymentService.handlePaymentCallback(callbackDto);
    }
}
