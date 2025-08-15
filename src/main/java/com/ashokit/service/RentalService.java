package com.ashokit.service;

import java.util.List;

import com.ashokit.dtos.RentalRequestDto;
import com.ashokit.model.RentalOrder;

public interface RentalService {
    RentalOrder createRentalOrder(RentalRequestDto request);
    
    List<RentalOrder> rentalOrders(Long companyId);
}
