package com.ashokit.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.dtos.RentalItemRequestDto;
import com.ashokit.dtos.RentalRequestDto;
import com.ashokit.model.Laptop;
import com.ashokit.model.RentalItem;
import com.ashokit.model.RentalOrder;
import com.ashokit.model.User;
import com.ashokit.repository.LaptopRepository;
import com.ashokit.repository.RentalOrderRepository;
import com.ashokit.repository.UserRepo;
import com.ashokit.service.RentalService;

@Service
public class RentalServiceImpl implements RentalService {

	 @Autowired
	    private UserRepo userRepository;
	    @Autowired
	    private LaptopRepository laptopRepository;
	    @Autowired
	    private RentalOrderRepository rentalOrderRepository;

	    @Override
	    public RentalOrder createRentalOrder(RentalRequestDto request) {
	        User user = userRepository.findById(request.getUserId())
	            .orElseThrow(() -> new RuntimeException("User not found"));

	        RentalOrder order = new RentalOrder();
	        order.setCompanyName(user.getName());
	        order.setStartDate(request.getStartDate());
	        order.setRentalMonths(request.getRentalMonths());
            order.setCompanyId(user);
	        List<RentalItem> items = new ArrayList<>();
	        double totalAmount = 0.0; 

	        for (RentalItemRequestDto itemDto : request.getItems()) {
	            Laptop laptop = laptopRepository.findById(itemDto.getLaptopId())
	                .orElseThrow(() -> new RuntimeException("Laptop not found"));
	      

	            if (!laptop.isAvailable()) {
	                throw new RuntimeException("Laptop with ID " + laptop.getId() + " is not available");
	            }

	            laptop.setAvailable(false);
	            laptopRepository.save(laptop);

	            RentalItem item = new RentalItem();
	            item.setLaptop(laptop);
	            item.setRentalOrder(order);
	            item.setPricePerMonth(laptop.getPricePerMonth());
	            items.add(item);

	           
	            totalAmount += laptop.getPricePerMonth() * request.getRentalMonths();
	        }

	        order.setItems(items);
	        order.setTotalAmount(totalAmount); 
	        return rentalOrderRepository.save(order);
	    }
        
	    @Override
	    public List<RentalOrder> rentalOrders(Long companyId) {
	        
	        Optional<User> companyOpt = userRepository.findById(companyId);

	        if (companyOpt.isPresent()) {
	            User company = companyOpt.get();
	            return rentalOrderRepository.findByCompanyId(company);
	        } else {
	         
	            return Collections.emptyList();
	        }
	    }

	    
	    
	    
	    
}
