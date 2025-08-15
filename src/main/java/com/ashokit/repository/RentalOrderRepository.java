package com.ashokit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.model.RentalOrder;
import com.ashokit.model.User;

public interface RentalOrderRepository extends JpaRepository<RentalOrder,Long> {
	
	
	public List<RentalOrder> findByCompanyId(User company);

}
