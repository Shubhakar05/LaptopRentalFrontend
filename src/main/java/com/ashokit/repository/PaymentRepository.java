package com.ashokit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Long>{
	
	 Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);              
	 Optional<Payment> findByRentalOrderId(Long rentalOrderId);
}
