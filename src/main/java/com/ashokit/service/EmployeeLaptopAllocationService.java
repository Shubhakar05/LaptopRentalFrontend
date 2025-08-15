package com.ashokit.service;

import java.time.LocalDate;

import com.ashokit.model.EmployeeLaptopAllocation;

public interface EmployeeLaptopAllocationService{
	
	 EmployeeLaptopAllocation allocateLaptop(Long employeeId, Long laptopId, LocalDate allocationStart, LocalDate allocationEnd);
	    EmployeeLaptopAllocation deallocateLaptop(Long allocationId, LocalDate deallocationDate);
}
