package com.ashokit.dtos;

import java.time.LocalDate;

import lombok.Data;


@Data
public class EmployeeLaptopAllocationDto {
    
	    private Long id;
	    private Long employeeId;
	    private Long laptopId;
	    private LocalDate allocationStart;
	    private LocalDate allocationEnd;

}
