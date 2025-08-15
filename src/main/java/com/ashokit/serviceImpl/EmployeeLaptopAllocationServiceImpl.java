package com.ashokit.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.model.EmployeeLaptopAllocation;
import com.ashokit.model.Employees;
import com.ashokit.model.Laptop;
import com.ashokit.repository.EmployeeLaptopAllocationRepo;
import com.ashokit.repository.EmployeesRepository;
import com.ashokit.repository.LaptopRepository;
import com.ashokit.service.EmployeeLaptopAllocationService;


@Service
public class EmployeeLaptopAllocationServiceImpl implements EmployeeLaptopAllocationService{
	
	
	@Autowired
	EmployeesRepository employeeRepo;
	
	@Autowired
	LaptopRepository lapRepo;
	
	@Autowired
	EmployeeLaptopAllocationRepo empAllRepo;

	@Override
    public EmployeeLaptopAllocation allocateLaptop(Long employeeId, Long laptopId, LocalDate allocationStart, LocalDate allocationEnd) {

       
        List<EmployeeLaptopAllocation> currentAllocations = empAllRepo.findCurrentAllocations(laptopId, allocationStart);
        if (!currentAllocations.isEmpty()) {
            throw new RuntimeException("Laptop is already allocated on " + allocationStart);
        }

        Employees employee = employeeRepo.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("Employee not found"));
        Laptop laptop = lapRepo.findById(laptopId)
            .orElseThrow(() -> new RuntimeException("Laptop not found"));

        EmployeeLaptopAllocation allocation = new EmployeeLaptopAllocation();
        allocation.setEmployee(employee);
        allocation.setLaptop(laptop);
        allocation.setAllocationStart(allocationStart);
        allocation.setAllocationEnd(allocationEnd);
        allocation.setCreatedAt(LocalDateTime.now());
        allocation.setUpdatedAt(LocalDateTime.now());

        return empAllRepo.save(allocation);
    }

    @Override
    public EmployeeLaptopAllocation deallocateLaptop(Long allocationId, LocalDate deallocationDate) {
        EmployeeLaptopAllocation allocation = empAllRepo.findById(allocationId)
            .orElseThrow(() -> new RuntimeException("Allocation record not found"));

      
        allocation.setAllocationEnd(deallocationDate);
        allocation.setUpdatedAt(LocalDateTime.now());

        return empAllRepo.save(allocation);
    }

}
